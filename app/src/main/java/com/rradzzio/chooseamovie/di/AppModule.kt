package com.rradzzio.chooseamovie.di

import com.rradzzio.chooseamovie.data.local.MoviesDao
import com.rradzzio.chooseamovie.data.local.model.MovieEntityMapper
import com.rradzzio.chooseamovie.data.remote.MovieApiService
import com.rradzzio.chooseamovie.data.remote.model.MovieDtoMapper
import com.rradzzio.chooseamovie.repositories.MovieRepository
import com.rradzzio.chooseamovie.repositories.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMovieRepository(
            dao: MoviesDao,
            apiService: MovieApiService,
            movieEntityMapper: MovieEntityMapper,
            movieDtoMapper: MovieDtoMapper
    ) = MovieRepositoryImpl(
            moviesDao = dao,
            movieApiService = apiService,
            movieEntityMapper = movieEntityMapper,
            movieDtoMapper = movieDtoMapper
    ) as MovieRepository

}