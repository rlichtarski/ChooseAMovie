package com.rradzzio.chooseamovie.presentation.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.rradzzio.chooseamovie.presentation.ui.adapters.AddMovieListAdapter
import com.rradzzio.chooseamovie.presentation.ui.adapters.MovieListAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class FragmentFactory @Inject constructor(
        private val movieListAdapter: MovieListAdapter,
        private val addMovieListAdapter: AddMovieListAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            MoviesFragment::class.java.name -> MoviesFragment(movieListAdapter)
            AddMovieFragment::class.java.name -> AddMovieFragment(addMovieListAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}