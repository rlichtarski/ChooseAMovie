package com.rradzzio.chooseamovie

import com.rradzzio.chooseamovie.domain.model.Movie

object FakeMovieDataTest {

    val movies = listOf(
        Movie(
            0,
            "Rush",
            "2013",
            "https://"
        ),
        Movie(
            1,
            "Cars",
            "2006",
            "https://"
        ),
        Movie(
            2,
            "Ford v Ferrari",
            "2019",
            "https://"
        )
    )

    val movie = Movie(0, "Matrix", "1999", "https://")

}