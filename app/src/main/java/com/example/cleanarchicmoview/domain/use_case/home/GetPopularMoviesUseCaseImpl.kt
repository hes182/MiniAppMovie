package com.example.cleanarchicmoview.domain.use_case.home

import com.example.cleanarchicmoview.R
import com.example.cleanarchicmoview.common.extension.handlerError
import com.example.cleanarchicmoview.common.utils.Resource
import com.example.cleanarchicmoview.common.utils.UiText
import com.example.cleanarchicmoview.data.local.entities.MovieEntity
import com.example.cleanarchicmoview.data.services.localStorage.LocalStorageService
import com.example.cleanarchicmoview.domain.mapper.MovieMapper
import com.example.cleanarchicmoview.domain.models.Movie
import com.example.cleanarchicmoview.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GetPopularMoviesUseCaseImpl @Inject constructor(
    private val repository: MovieRepository,
    private val localStorageService: LocalStorageService,
    private val mapper: MovieMapper
) : GetPopularMovieUseCase {
    override suspend fun getPopularMovies(page: Int): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getPopularMovies(page = page).results
            localStorageService.setLastUpdateTime(System.currentTimeMillis())
            emit(Resource.Success(data = response.map(mapper::fromDtoToDomain)))
        } catch (e: HttpException) {
            emit(Resource.Error(e.handlerError()))
        } catch (e: IOException) {
            emit(Resource.Error(UiText.StringResource(R.string.couldntReachServerError)))
        }
    }
}