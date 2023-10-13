package com.example.cleanarchicmoview.domain.repositories

import com.example.cleanarchicmoview.data.local.entities.MovieEntity
import com.example.cleanarchicmoview.data.remote.models.MovieDto
import com.example.cleanarchicmoview.data.remote.models.response.PopularMovieListResponseDto

interface MovieRepository {
    suspend fun getPopularMovies(page: Int) : PopularMovieListResponseDto

    suspend fun getCachedPopularMovies(page: Int) : List<MovieEntity>

    suspend fun getMovie(id: Long): MovieDto


    suspend fun getPopularMovieFromDatabse(page: Int) : List<MovieEntity>

    suspend fun saveMovieFavorite(movie: MovieDto)

    suspend fun deletePopularMoviesFromDatabase()

    suspend fun getMovieFavByIdDB(id: Long): MovieEntity

}