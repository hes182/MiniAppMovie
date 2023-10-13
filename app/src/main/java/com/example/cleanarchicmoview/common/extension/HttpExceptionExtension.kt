package com.example.cleanarchicmoview.common.extension

import com.example.cleanarchicmoview.R
import com.example.cleanarchicmoview.common.utils.UiText
import com.example.cleanarchicmoview.data.remote.models.ErrorDto
import com.example.cleanarchicmoview.data.remote.models.toErrorModel
import com.google.gson.Gson
import retrofit2.HttpException

val gson = Gson()

@Synchronized
fun HttpException.handlerError() : UiText {
    val errorString = this.response()?.errorBody()?.toString()
    errorString?.let {
        val errorModel = gson.fromJson(errorString, ErrorDto::class.java)?.toErrorModel()
        if (errorModel?.error != null) {
            return UiText.DynamicString(errorModel.error)
        } else {
            return UiText.StringResource(R.string.unexpectedError)
        }
    }
    return this.localizedMessage?.let { UiText.DynamicString(it) }
        ?: UiText.StringResource(R.string.unexpectedError)
}