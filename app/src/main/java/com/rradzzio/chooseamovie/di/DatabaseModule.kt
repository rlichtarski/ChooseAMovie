package com.rradzzio.chooseamovie.di

import android.content.Context
import androidx.room.Room
import com.rradzzio.chooseamovie.data.local.MovieDatabase
import com.rradzzio.chooseamovie.data.local.model.MovieEntityMapper
import com.rradzzio.chooseamovie.util.Constants.Companion.MOVIE_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideMovieEntityMapper(): MovieEntityMapper = MovieEntityMapper()

    @Singleton
    @Provides
    fun provideMovieDatabase(@ApplicationContext context: Context) = Room
            .databaseBuilder(context, MovieDatabase::class.java, MOVIE_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideMovieDao(db: MovieDatabase) = db.moviesDao()

}