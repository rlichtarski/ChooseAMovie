package com.rradzzio.chooseamovie.repositories

import com.rradzzio.chooseamovie.data.local.MoviesDao
import com.rradzzio.chooseamovie.data.local.model.MovieEntityMapper
import com.rradzzio.chooseamovie.data.remote.MovieApiService
import com.rradzzio.chooseamovie.data.remote.model.MovieDtoMapper
import com.rradzzio.chooseamovie.domain.model.Movie
import com.rradzzio.chooseamovie.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
        private val moviesDao: MoviesDao,
        private val movieApiService: MovieApiService,
        private val movieEntityMapper: MovieEntityMapper,
        private val movieDtoMapper: MovieDtoMapper
) : MovieRepository {

    override suspend fun insertMovieList(movieList: List<Movie>) {
        moviesDao.insertMovieList(movieEntityMapper.fromDomainListToEntity(movieList))
    }

    override suspend fun insertMovie(movie: Movie) {
        moviesDao.insertMovie(movieEntityMapper.mapFromDomainModel(movie))
    }

    override suspend fun deleteMovie(movie: Movie) {
        moviesDao.deleteMovie(movieEntityMapper.mapFromDomainModel(movie))
    }

    override suspend fun deleteMovieList(movieList: List<Movie>) {
        moviesDao.deleteMovieList(movieEntityMapper.fromDomainListToEntity(movieList))
    }

    override fun returnAllMovies(): Flow<List<Movie>> {
        return moviesDao.returnAllMovies().map {
            movieEntityMapper.fromEntityListToDomain(it)
        }
    }

    override fun getMovies(movieSearchQuery: String): Flow<Resource<List<Movie>>> = flow {
        try {
            val response = movieApiService.getMovies(movieSearchQuery)
            Timber.d("getMovies: ${response.body()}")
            if (response.isSuccessful) {
                response.body()?.let { movieResultDto ->

                    val movieResult = movieResultDto.search?.let {
                        movieDtoMapper.fromDtoListToDomain(it)
                    }
                    movieResult?.forEach { movie ->
                        Timber.d("movie: $movie")
                    }

                    emit(
                            Resource.success(
                                    movieResultDto.search?.let {
                                        movieDtoMapper.fromDtoListToDomain(it)
                                    }
                            )
                    )
                } ?: emit(Resource.error(
                        "An unknown error occurred",
                        null
                ))
            } else {
                emit(Resource.error(
                        "An unknown error occurred",
                        null
                ))
            }
        } catch (e: Exception) {
            Timber.e("exception: ${e.message}")
            emit(Resource.error("Couldn't reach the server. Check internet connection", null))
        }
    }

    private fun returnUnknownError(): Resource<Flow<List<Movie>>> {
        return Resource.error(
                "An unknown error occurred",
                null
        )
    }

}