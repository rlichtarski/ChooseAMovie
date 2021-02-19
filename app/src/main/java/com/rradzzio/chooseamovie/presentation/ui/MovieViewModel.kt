package com.rradzzio.chooseamovie.presentation.ui

import androidx.lifecycle.*
import com.rradzzio.chooseamovie.domain.model.Movie
import com.rradzzio.chooseamovie.repositories.MovieRepository
import com.rradzzio.chooseamovie.util.Event
import com.rradzzio.chooseamovie.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
        private val movieRepository: MovieRepository,
        private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _movies = MutableLiveData<Event<Resource<List<Movie>>>>()
    val movies: LiveData<Event<Resource<List<Movie>>>> get() = _movies

    private val _moviesFromDb = MutableLiveData<List<Movie>>()
    val moviesFromDb: LiveData<List<Movie>> get() = _moviesFromDb

    fun getMovies(searchQuery: String) = viewModelScope.launch {
        if(searchQuery.isBlank()) {
            _movies.postValue(
                Event(Resource.error("The fields must not be empty", null))
            )
            return@launch
        }
        movieRepository.getMovies(searchQuery).collect {
            _movies.postValue(Event(it))
        }
    }

    fun allMoviesFromDb() = viewModelScope.launch {
        movieRepository.returnAllMovies().collect {
            _moviesFromDb.postValue(it)
        }
    }

    fun insertMovieList(movieList: List<Movie>) = viewModelScope.launch {
        movieRepository.insertMovieList(movieList = movieList)
    }

    fun insertMovie(movie: Movie) = viewModelScope.launch{
        movieRepository.insertMovie(movie)
    }

    fun deleteMovie(movie: Movie) = viewModelScope.launch {
        movieRepository.deleteMovie(movie)
    }

    fun deleteMovieList(movieList: List<Movie>) = viewModelScope.launch {
        movieRepository.deleteMovieList(movieList)
    }

}