package com.rradzzio.chooseamovie.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rradzzio.chooseamovie.data.local.model.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao

}