package com.rradzzio.chooseamovie.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rradzzio.chooseamovie.R
import com.rradzzio.chooseamovie.databinding.FragmentAddMovieBinding
import com.rradzzio.chooseamovie.databinding.FragmentMoviesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddMovieFragment : Fragment(R.layout.fragment_add_movie) {

    private var _binding: FragmentAddMovieBinding? = null

    private val binding get() = _binding!!

    lateinit var viewModel: MovieViewModel

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

        viewModel = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}