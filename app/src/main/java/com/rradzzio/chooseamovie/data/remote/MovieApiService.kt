package com.rradzzio.chooseamovie.data.remote

import com.rradzzio.chooseamovie.data.remote.model.MovieResultDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("/")
    fun getMovies(
            @Query("s") movieSearchQuery: String,
            @Query("r") returnType: String = "json"
    ): Response<MovieResultDto>

}