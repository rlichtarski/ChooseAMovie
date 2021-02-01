package com.rradzzio.chooseamovie.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieResultDto(

        @Json(name = "Search")
        val search: List<MovieDto>,

        @Json(name = "Response")
        val response: String

)
