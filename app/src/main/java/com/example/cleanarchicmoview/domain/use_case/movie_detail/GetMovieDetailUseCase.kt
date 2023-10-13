package com.example.cleanarchicmoview.domain.use_case.movie_detail

import com.example.cleanarchicmoview.common.utils.Resource
import com.example.cleanarchicmoview.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface GetMovieDetailUseCase {
    suspend fun getMovieById(id: Long): Flow<Resource<Movie>>

    suspend fun getFavMovieById(id: Long): Flow<Resource<Movie>>
}