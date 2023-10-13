package com.example.cleanarchicmoview.common.utils

import com.example.cleanarchicmoview.R
import com.example.cleanarchicmoview.common.extension.handlerError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException


fun<T, K> performRequest(mapper: (response:T) -> K, networkCall: suspend() -> T) : Flow<Resource<K>> = flow {
    try {
        emit(Resource.Loading())
        val response = networkCall.invoke()
        emit(Resource.Success(data = mapper(response)))
    } catch (e: HttpException) {
        emit(Resource.Error(e.handlerError()))
    } catch (e: IOException) {
        emit(Resource.Error(UiText.StringResource(R.string.couldntReachServerError)))
    }
}