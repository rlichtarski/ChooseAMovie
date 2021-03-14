package com.rradzzio.chooseamovie.presentation.ui

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.Espresso.pressBackUnconditionally
import androidx.test.filters.MediumTest
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
class AddMovieFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun pressBackButton_popBackStack() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<AddMovieFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        pressBack()

        verify(navController).popBackStack()
    }

}