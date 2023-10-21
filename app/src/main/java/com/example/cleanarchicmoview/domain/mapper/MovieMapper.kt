package com.example.cleanarchicmoview.domain.mapper

import com.example.cleanarchicmoview.data.local.entities.MovieEntity
import com.example.cleanarchicmoview.data.local.entities.MoviePopulerEntity
import com.example.cleanarchicmoview.data.remote.models.MovieDto
import com.example.cleanarchicmoview.data.remote.models.toGenreEntity
import com.example.cleanarchicmoview.domain.models.Movie

class MovieMapper {

    fun fromDtoToEntity(movieDto: MovieDto) : MovieEntity = with(movieDto) {
        MovieEntity(
            id = id,
            posterPath = posterPath ?: "",
            releaseDate = releaseDate,
            voteAverage = voteAverage,
            title = title,
            overview = overview,
            genres = genres?.map { it.toGenreEntity() } ?: emptyList(),
            runtime = runtime,
            originalTitles = originalTitle,
            popularity = popularity,
            revenue = revenue,
            status = status
        )
    }

    fun fromDtoToDomain(movieDto: MovieDto): Movie = with(movieDto) {
        Movie(
            id = id,
            posterPath = posterPath ?: "",
            releaseDate = releaseDate,
            voteAverage = voteAverage,
            title = title,
            overview = overview,
            genre = if (genres != null && genres.isNotEmpty()) genres[0].name else "",
            runtime = runtime,
        )
    }

    fun fromEntityToDomain(movieEntity: MovieEntity) : Movie = with(movieEntity) {
        Movie(
            id = id,
            posterPath = posterPath ?: "",
            releaseDate = releaseDate,
            voteAverage = voteAverage,
            title = title,
            overview = overview,
            genre = if (genres != null && genres.isNotEmpty()) genres[0].name else "",
            runtime = runtime
        )
    }

    fun fromDtoToEntityMovPop(movieDto: MovieDto) : MoviePopulerEntity = with(movieDto) {
        MoviePopulerEntity(
            id = id,
            posterPath = posterPath ?: "",
            releaseDate = releaseDate,
            voteAverage = voteAverage,
            title = title,
            overview = overview,
            genres = genres?.map { it.toGenreEntity() } ?: emptyList(),
            runtime = runtime,
            originalTitle = originalTitle,
            popularity = popularity,
            revenue = revenue,
            status = status
        )
    }
}