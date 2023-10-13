package com.example.cleanarchicmoview.domain.use_case.home

import com.example.cleanarchicmoview.common.utils.Resource
import com.example.cleanarchicmoview.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface GetPopularMovieUseCase {
    suspend fun getPopularMovies(page: Int) : Flow<Resource<List<Movie>>>
}