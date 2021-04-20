package com.rradzzio.chooseamovie.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rradzzio.chooseamovie.R
import com.rradzzio.chooseamovie.databinding.FragmentAddMovieBinding
import com.rradzzio.chooseamovie.presentation.ui.adapters.AddMovieListAdapter
import com.rradzzio.chooseamovie.util.Constants
import com.rradzzio.chooseamovie.util.Status
import com.rradzzio.chooseamovie.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class AddMovieFragment @Inject constructor(
    val addMovieListAdapter: AddMovieListAdapter,
    var viewModel: MovieViewModel? = null
) : Fragment(R.layout.fragment_add_movie) {

    private var _binding: FragmentAddMovieBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = viewModel ?: ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)
        setupRecyclerView()
        subscribeObservers()

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        binding.btnSearchMovie.setOnClickListener {
            searchMovie()
        }
    }

    private fun subscribeObservers() {
        viewModel?.movies?.observe(viewLifecycleOwner,  { event ->
            event.getContentIfNotHandled()?.let {
                when(it.status) {
                    Status.SUCCESS -> {
                        Timber.d("SUCCESS")
                        it.data?.let { movieList ->
                            addMovieListAdapter.movieItems = movieList
                        }
                    }

                    Status.ERROR -> {
                        Timber.e("Error fetching movies... ${it.message}")
                    }

                    Status.LOADING -> {
                        Timber.d("LOADING...")
                    }
                }
            }
        })

    }

    private fun searchMovie() {
        hideKeyboard()
        if(binding.etMovieTitle.text.toString().isBlank()) {
            Toast.makeText(activity, Constants.EMPTY_MOVIE_SEARCH, Toast.LENGTH_SHORT).show()
            return
        }
        Timber.d("searchMovie...")
        binding.focusableView.requestFocus()
        viewModel?.getMovies(binding.etMovieTitle.text.toString())
    }

    private fun setupRecyclerView() {
        binding.searchedMoviesRv.apply {
            adapter = addMovieListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        addMovieListAdapter.setOnItemFabClickListener {
            viewModel?.insertMovie(it)
            Toast.makeText(activity, Constants.ADDED_MOVIE_TO_DB, Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

