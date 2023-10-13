package com.example.cleanarchicmoview.data.remote.api_service

import com.example.cleanarchicmoview.data.remote.models.MovieDto
import com.example.cleanarchicmoview.data.remote.models.response.PopularMovieListResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): PopularMovieListResponseDto

    @GET("movie/{id}")
    suspend fun getMovie(@Path("id") id: Long): MovieDto

}