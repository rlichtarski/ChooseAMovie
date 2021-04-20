package com.rradzzio.chooseamovie.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rradzzio.chooseamovie.R
import com.rradzzio.chooseamovie.databinding.FragmentMoviesBinding
import com.rradzzio.chooseamovie.presentation.ui.adapters.MovieListAdapter
import com.rradzzio.chooseamovie.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment @Inject constructor(
    val movieListAdapter: MovieListAdapter,
    var viewModel: MovieViewModel? = null
) : Fragment(R.layout.fragment_movies) {

    private var _binding: FragmentMoviesBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            viewModel ?: ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)
        setupRecyclerView()
        subscribeObservers()

        viewModel?.returnAllMoviesFromDb()

        binding.fabAddMovie.setOnClickListener {
            findNavController().navigate(
                MoviesFragmentDirections.actionMoviesFragmentToAddMovieFragment()
            )
        }

    }

    private fun subscribeObservers() {
        viewModel?.moviesFromDb?.observe(viewLifecycleOwner, { movieList ->
            movieList.forEach { movie ->
                Timber.d("MoviesFragment: Movie: ${movie.title}")
            }
            movieListAdapter.movieItems = movieList
        })
    }

    private val itemTouchHelperCallback = object : SimpleCallback(
        0,
        LEFT or RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ) = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.layoutPosition
            val item = movieListAdapter.movieItems[position]
            viewModel?.deleteMovie(item)
            Toast.makeText(activity, Constants.DELETED_MOVIE_FROM_DB, Toast.LENGTH_LONG).show()
        }

    }

    private fun setupRecyclerView() {
        binding.rvMovieItems.apply {
            adapter = movieListAdapter
            layoutManager = LinearLayoutManager(requireContext())
            ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(this)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}