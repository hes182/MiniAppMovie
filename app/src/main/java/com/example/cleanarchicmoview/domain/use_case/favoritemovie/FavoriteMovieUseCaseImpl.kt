package com.example.cleanarchicmoview.domain.use_case.favoritemovie


import com.example.cleanarchicmoview.R
import com.example.cleanarchicmoview.common.extension.handlerError
import com.example.cleanarchicmoview.common.utils.Resource
import com.example.cleanarchicmoview.common.utils.UiText
import com.example.cleanarchicmoview.data.local.entities.MovieEntity
import com.example.cleanarchicmoview.domain.mapper.MovieMapper
import com.example.cleanarchicmoview.domain.models.Movie
import com.example.cleanarchicmoview.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FavoriteMovieUseCaseImpl @Inject constructor(
    private val repository: MovieRepository,
    private val mapper: MovieMapper
) : FavoriteMoviesUseCase {
    override suspend fun getFavoriteMovie(page: Int): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading())
            val response: List<MovieEntity> = repository.getPopularMovieFromDatabse(page)
            if (response.isNotEmpty()) {
                emit(Resource.Success(data = response.map(mapper::fromEntityToDomain)))
            } else {
                emit(Resource.Success(data = null))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.handlerError()))
        } catch (e: IOException) {
            emit(Resource.Error(UiText.StringResource(R.string.couldntReachServerError)))
        }

    }
}