package com.example.cleanarchicmoview.domain.use_case.movie_detail

import com.example.cleanarchicmoview.R
import com.example.cleanarchicmoview.common.extension.handlerError
import com.example.cleanarchicmoview.common.utils.Resource
import com.example.cleanarchicmoview.common.utils.UiText
import com.example.cleanarchicmoview.common.utils.performRequest
import com.example.cleanarchicmoview.data.local.entities.MovieEntity
import com.example.cleanarchicmoview.data.remote.models.MovieDto
import com.example.cleanarchicmoview.domain.mapper.MovieMapper
import com.example.cleanarchicmoview.domain.models.Movie
import com.example.cleanarchicmoview.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMovieDetailUseCaseImpl @Inject constructor(
    private val repository: MovieRepository,
    private val movieMapper: MovieMapper
) : GetMovieDetailUseCase {
    override suspend fun getMovieById(id: Long) =
        performRequest(
            mapper = movieMapper::fromDtoToDomain,
            networkCall = {repository.getMovie(id)}
        )

    override suspend fun getFavMovieById(id: Long): Flow<Resource<Movie>> = flow {
        try {
            emit(Resource.Loading())
            val result: MovieEntity = repository.getMovieFavByIdDB(id)
            if (result == null) {
                emit(Resource.Success(data = null))
            } else {
                emit(Resource.Success(data = movieMapper.fromEntityToDomain(result)))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.handlerError()))
        } catch (e: IOException) {
            emit(Resource.Error(UiText.StringResource(R.string.couldntReachServerError)))
        }
    }


}