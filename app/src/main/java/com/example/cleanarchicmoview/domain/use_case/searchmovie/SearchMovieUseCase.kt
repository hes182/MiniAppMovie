package com.example.cleanarchicmoview.domain.use_case.searchmovie

import com.example.cleanarchicmoview.common.utils.Resource
import com.example.cleanarchicmoview.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface SearchMovieUseCase {
    suspend fun searchMovie(query: String): Flow<Resource<List<Movie>>>
}