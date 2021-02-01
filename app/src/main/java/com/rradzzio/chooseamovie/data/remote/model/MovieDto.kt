package com.rradzzio.chooseamovie.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDto(

        @Json(name = "Title")
        val title: String? = null,

        @Json(name = "Year")
        val year: String? = null,

        @Json(name = "Poster")
        val poster: String? = null

)
