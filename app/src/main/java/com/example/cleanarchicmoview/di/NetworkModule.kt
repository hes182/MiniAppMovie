package com.example.cleanarchicmoview.di

import android.content.Context
import android.util.Log
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.cleanarchicmoview.BuildConfig
import com.example.cleanarchicmoview.data.remote.api_service.MovieApiService
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): MovieApiService {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build().create(
                MovieApiService::class.java
            )
    }

    @Provides
    @Singleton
    fun provideOkHttpCLientBuilder(): OkHttpClient.Builder = OkHttpClient.Builder()
        .readTimeout(20, TimeUnit.SECONDS)
        .connectTimeout(20, TimeUnit.SECONDS)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        builder: OkHttpClient.Builder
    ): OkHttpClient {
        builder.addInterceptor(
            Interceptor { chain ->
                val original = chain.request()
                val url = original.url.newBuilder().addQueryParameter(
                    "api_key",
                    BuildConfig.API_KEY
                ).build()
                val requestBuilder = original.newBuilder().url(url)
                chain.proceed(requestBuilder.build())
            }
        )
        return builder.build()
    }

}