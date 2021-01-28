package com.rradzzio.chooseamovie.data.local.model

import com.rradzzio.chooseamovie.domain.model.Movie
import com.rradzzio.chooseamovie.domain.util.DomainMapper

class MovieEntityMapper : DomainMapper<MovieEntity, Movie> {

    override fun mapToDomainModel(model: MovieEntity): Movie {
        return Movie(
            title = model.title,
            year =  model.year,
            poster = model.poster
        )
    }

    override fun mapFromDomainModel(domainModel: Movie): MovieEntity {
        return MovieEntity(
            title = domainModel.title,
            year = domainModel.year,
            poster = domainModel.poster
        )
    }
}