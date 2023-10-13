package com.example.cleanarchicmoview.data.remote.models

import com.example.cleanarchicmoview.domain.models.ErrorModel

data class ErrorDto(val error: String?)

fun ErrorDto.toErrorModel() : ErrorModel = ErrorModel(error = error)