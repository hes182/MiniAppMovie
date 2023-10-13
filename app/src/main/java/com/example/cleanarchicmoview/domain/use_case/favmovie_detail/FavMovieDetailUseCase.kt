package com.example.cleanarchicmoview.domain.use_case.favmovie_detail

import com.example.cleanarchicmoview.common.utils.Resource
import com.example.cleanarchicmoview.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface FavMovieDetailUseCase {
    suspend fun getFavMovieById(id: Long) : Flow<Resource<Movie>>
}