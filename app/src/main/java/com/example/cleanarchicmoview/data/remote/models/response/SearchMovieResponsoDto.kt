package com.example.cleanarchicmoview.data.remote.models.response

import com.example.cleanarchicmoview.data.remote.models.MovieDto
import com.google.gson.annotations.SerializedName

data class SearchMovieResponsoDto(
    val page: Int,
    val results: List<MovieDto>,
    @SerializedName("total_page")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)