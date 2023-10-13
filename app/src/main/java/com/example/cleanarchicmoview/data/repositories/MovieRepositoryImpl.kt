package com.example.cleanarchicmoview.data.repositories

import com.example.cleanarchicmoview.data.local.MovieDao
import com.example.cleanarchicmoview.data.local.entities.MovieEntity
import com.example.cleanarchicmoview.data.remote.api_service.MovieApiService
import com.example.cleanarchicmoview.data.remote.models.MovieDto
import com.example.cleanarchicmoview.data.remote.models.response.PopularMovieListResponseDto
import com.example.cleanarchicmoview.domain.mapper.MovieMapper
import com.example.cleanarchicmoview.domain.repositories.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApiService,
    private val dao: MovieDao,
    private val mapper: MovieMapper
) : MovieRepository {
    override suspend fun getPopularMovies(page: Int): PopularMovieListResponseDto = api.getPopularMovies(page)

    override suspend fun getCachedPopularMovies(page: Int): List<MovieEntity> = dao.getPopularMovies()

    override suspend fun getMovie(id: Long): MovieDto = api.getMovie(id)

    override suspend fun getPopularMovieFromDatabse(page: Int): List<MovieEntity> = dao.getPopularMovies()

    override suspend fun saveMovieFavorite(movie: MovieDto) = dao.insertMoviesFav(mapper.fromDtoToEntity(movie))

    override suspend fun deletePopularMoviesFromDatabase() = dao.deleteAllMoview()

    override suspend fun getMovieFavByIdDB(id: Long): MovieEntity = dao.getFavMovieByID(id)
}