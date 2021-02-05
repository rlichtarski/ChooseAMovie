package com.rradzzio.chooseamovie.repositories

import com.rradzzio.chooseamovie.data.local.model.MovieEntity
import com.rradzzio.chooseamovie.data.remote.model.MovieResultDto
import com.rradzzio.chooseamovie.domain.model.Movie
import com.rradzzio.chooseamovie.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun insertMovieList(movieList: List<MovieEntity>)

    suspend fun insertMovie(movie: MovieEntity)

    suspend fun deleteMovie(movie: MovieEntity)

    suspend fun deleteMovieList(movieList: List<MovieEntity>)

    fun returnAllMovies(): Flow<List<Movie>>

    fun getMovies(
            movieSearchQuery: String
    ): Flow<Resource<List<Movie>>>

}