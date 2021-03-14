package com.rradzzio.chooseamovie.presentation.ui

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.rradzzio.chooseamovie.R
import com.rradzzio.chooseamovie.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.*
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class MoviesFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun clickAddMovieButton_navigateToAddMovieFragment() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<MoviesFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.fabAddMovie)).perform(click())

        verify(navController).navigate(
            MoviesFragmentDirections.actionMoviesFragmentToAddMovieFragment()
        )
    }

}