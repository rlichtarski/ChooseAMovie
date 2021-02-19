package com.rradzzio.chooseamovie.repositories

import com.rradzzio.chooseamovie.data.local.model.MovieEntity
import com.rradzzio.chooseamovie.domain.model.Movie
import com.rradzzio.chooseamovie.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class FakeMovieRepository : MovieRepository {

    private val movies = mutableListOf<MovieEntity>()

    private val observableMovies: Flow<List<MovieEntity>> = flow {
        emit(movies)
    }

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override suspend fun insertMovieList(movieList: List<Movie>) {
        for (movie in movieList) {
            movies.add(movie)
        }
    }

    override suspend fun insertMovie(movie: MovieEntity) {
        movies.add(movie)
    }

    override suspend fun deleteMovie(movie: MovieEntity) {
        movies.remove(movie)
    }

    override suspend fun deleteMovieList(movieList: List<MovieEntity>) {
        for (movie in movies) {
            movies.remove(movie)
        }
    }

    override fun returnAllMovies(): Flow<List<Movie>> = flow {
        observableMovies.map { movieEntityList ->
            movieEntityList.map {
                mapToDomainModel(it)
            }
        }

    }

    override fun getMovies(movieSearchQuery: String): Flow<Resource<List<Movie>>> {
        return if (shouldReturnNetworkError) {
            flow {
                Resource.error("Error", null)
            }
        } else {
            flow {
                Resource.success(listOf(movies))
            }
        }
    }

    private fun mapToDomainModel(model: MovieEntity): Movie {
        return Movie(
                title = model.title,
                year = model.year,
                poster = model.poster
        )
    }

}