package com.rradzzio.chooseamovie.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.rradzzio.chooseamovie.R
import com.rradzzio.chooseamovie.databinding.FragmentMoviesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment(R.layout.fragment_movies) {

    private var _binding: FragmentMoviesBinding? = null

    private val binding get() = _binding!!

    lateinit var viewModel: MovieViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)

        binding.fabAddMovie.setOnClickListener {
            findNavController().navigate(
                MoviesFragmentDirections.actionMoviesFragmentToAddMovieFragment()
            )
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}