package com.example.cleanarchicmoview.domain.use_case.favoritemovie

import com.example.cleanarchicmoview.common.utils.Resource
import com.example.cleanarchicmoview.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface FavoriteMoviesUseCase {
    suspend fun getFavoriteMovie(page: Int) : Flow<Resource<List<Movie>>>
}