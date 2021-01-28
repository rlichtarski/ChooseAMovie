package com.rradzzio.chooseamovie.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pk")
    val pk: Int? = null,

    @ColumnInfo(name = "title")
    val title: String? = null,

    @ColumnInfo(name = "year")
    val year: String? = null,

    @ColumnInfo(name = "poster")
    val poster: String? = null
)
