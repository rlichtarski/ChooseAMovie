package com.rradzzio.chooseamovie.data.remote.model

import com.rradzzio.chooseamovie.domain.model.Movie
import com.rradzzio.chooseamovie.domain.util.DomainMapper

class MovieDtoMapper : DomainMapper<MovieDto, Movie> {

    override fun mapToDomainModel(model: MovieDto): Movie {
        return Movie(
                title = model.title,
                year = model.year,
                poster = model.poster
        )
    }

    override fun mapFromDomainModel(domainModel: Movie): MovieDto {
        return MovieDto(
                title = domainModel.title,
                year = domainModel.year,
                poster = domainModel.poster
        )
    }

    fun fromDtoListToDomain(movieDtoList: List<MovieDto>): List<Movie> {
        return movieDtoList.map { mapToDomainModel(it) }
    }

    fun fromDomainListToDto(movieList: List<Movie>): List<MovieDto> {
        return movieList.map { mapFromDomainModel(it) }
    }

}