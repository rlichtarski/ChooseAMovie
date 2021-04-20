package com.rradzzio.chooseamovie.presentation.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.rradzzio.chooseamovie.presentation.ui.adapters.AddMovieListAdapter
import com.rradzzio.chooseamovie.presentation.ui.adapters.MovieListAdapter
import com.rradzzio.chooseamovie.repositories.FakeMovieRepositoryAndroidTest
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class TestFragmentFactory @Inject constructor(
        private val movieListAdapter: MovieListAdapter,
        private val addMovieListAdapter: AddMovieListAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            MoviesFragment::class.java.name -> MoviesFragment(
                movieListAdapter,
                MovieViewModel(FakeMovieRepositoryAndroidTest())
            )
            AddMovieFragment::class.java.name -> AddMovieFragment(
                addMovieListAdapter,
                MovieViewModel(FakeMovieRepositoryAndroidTest())
            )
            else -> super.instantiate(classLoader, className)
        }
    }
}