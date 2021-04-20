package com.rradzzio.chooseamovie.presentation.ui

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import com.google.common.truth.Truth.assertThat
import androidx.test.filters.MediumTest
import com.rradzzio.chooseamovie.FakeMovieData
import com.rradzzio.chooseamovie.R
import com.rradzzio.chooseamovie.domain.model.Movie
import com.rradzzio.chooseamovie.launchFragmentInHiltContainer
import com.rradzzio.chooseamovie.presentation.ToastMatcher
import com.rradzzio.chooseamovie.presentation.clickRecyclerViewFabAction
import com.rradzzio.chooseamovie.presentation.hasItemAtPosition
import com.rradzzio.chooseamovie.util.Constants
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class AddMovieFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var testFragmentFactory: TestFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun pressBackButton_popBackStack() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<AddMovieFragment>(
            fragmentFactory = testFragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
        }

        pressBack()

        verify(navController).popBackStack()
    }

    @Test
    fun test_isRecyclerViewVisible_whenAddMovieFragmentLaunches() {
        launchFragmentInHiltContainer<AddMovieFragment>(
            fragmentFactory = testFragmentFactory
        ) {
            addMovieListAdapter.movieItems = FakeMovieData.movies
        }
        onView(withId(R.id.searchedMoviesRv)).check(matches(isDisplayed()))
    }

    @Test
    fun pressAddMovieButton_insertMovieIntoDb() {
        var testMovieViewModel: MovieViewModel? = null
        launchFragmentInHiltContainer<AddMovieFragment>(
            fragmentFactory = testFragmentFactory
        ) {
            testMovieViewModel = viewModel
            addMovieListAdapter.movieItems = FakeMovieData.movies
            testMovieViewModel?.returnAllMoviesFromDb()
        }

        onView(withId(R.id.searchedMoviesRv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0, clickRecyclerViewFabAction(R.id.fabAddMovie)
            )
        )

        assertThat(testMovieViewModel?.moviesFromDb?.value)
            .contains(FakeMovieData.movies[0])
    }

    @Test
    fun insertMovieIntoDB_doesElementsMatchFromRecyclerView() {
        var testMovieViewModel: MovieViewModel? = null
        launchFragmentInHiltContainer<AddMovieFragment>(
            fragmentFactory = testFragmentFactory
        ) {
            testMovieViewModel = viewModel
            addMovieListAdapter.movieItems = FakeMovieData.movies
            testMovieViewModel?.returnAllMoviesFromDb()
        }

        onView(withId(R.id.searchedMoviesRv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0, clickRecyclerViewFabAction(R.id.fabAddMovie)
            )
        )

        onView(withText(Constants.ADDED_MOVIE_TO_DB))
            .inRoot(ToastMatcher())
            .check(matches(isDisplayed()))

        val moviesFromDb = testMovieViewModel?.moviesFromDb?.value

        onView(withId(R.id.searchedMoviesRv)).check(matches(hasItemAtPosition(0, hasDescendant(withText(moviesFromDb?.get(0)?.title)))))
        onView(withId(R.id.searchedMoviesRv)).check(matches(hasItemAtPosition(0, hasDescendant(withText(moviesFromDb?.get(0)?.year)))))

    }

    @Test
    fun clickSearchButtonWithEmptyEditText_isToastShown() {
        launchFragmentInHiltContainer<AddMovieFragment>(
            fragmentFactory = testFragmentFactory
        )

        onView(withId(R.id.etMovieTitle)).perform(typeText(""))

        onView(withId(R.id.btnSearchMovie)).perform(click())

        onView(withText(Constants.EMPTY_MOVIE_SEARCH))
            .inRoot(ToastMatcher())
            .check(matches(isDisplayed()))

    }

}