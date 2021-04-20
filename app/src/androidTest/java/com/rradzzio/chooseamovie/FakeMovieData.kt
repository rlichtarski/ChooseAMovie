package com.rradzzio.chooseamovie

import com.rradzzio.chooseamovie.domain.model.Movie

object FakeMovieData {

    val movies = listOf(
        Movie(
            "Rush",
            "2013",
            "https://"
        ),
        Movie(
            "Cars",
            "2006",
            "https://"
        )
    )

    val movie = Movie("Matrix", "1999", "https://")

}