package com.example.cleanarchicmoview.data.remote.models

import com.example.cleanarchicmoview.data.local.entities.GenreEntity

data class GenreDto(
    val id: Int,
    val name: String
)

fun GenreDto.toGenreEntity() : GenreEntity = GenreEntity(
    id = id,
    name = name
)