package com.example.cleanarchicmoview.domain.use_case.favmovie_detail

import com.example.cleanarchicmoview.common.utils.Resource
import com.example.cleanarchicmoview.common.utils.performRequest
import com.example.cleanarchicmoview.domain.mapper.MovieMapper
import com.example.cleanarchicmoview.domain.models.Movie
import com.example.cleanarchicmoview.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavMovieDetailUseCaseImpl @Inject constructor(
    private val repository: MovieRepository,
    private val mapper: MovieMapper
) : FavMovieDetailUseCase{
    override suspend fun getFavMovieById(id: Long) =
        performRequest(
            mapper = mapper::fromDtoToDomain,
            networkCall = {repository.getMovie(id)}
        )
}