package com.example.cleanarchicmoview.domain.use_case.searchmovie

import com.example.cleanarchicmoview.common.utils.Resource
import com.example.cleanarchicmoview.common.utils.performRequest
import com.example.cleanarchicmoview.domain.mapper.MovieMapper
import com.example.cleanarchicmoview.domain.models.Movie
import com.example.cleanarchicmoview.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMovieUseCaseImpl @Inject constructor(
    private val repository: MovieRepository,
    private val mapper: MovieMapper
) : SearchMovieUseCase {
    override suspend fun searchMovie(query: String): Flow<Resource<List<Movie>>> =
        performRequest(
            mapper = { response -> response.results.map(mapper::fromDtoToDomain) },
            networkCall = {repository.searchMovie(query)}
        )
}