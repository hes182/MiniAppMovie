package com.example.cleanarchicmoview.di

import com.example.cleanarchicmoview.data.services.localStorage.KeyValueStore
import com.example.cleanarchicmoview.data.services.localStorage.LocalStorageService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Singleton
    @Provides
    fun provideLocalStorageService(gson: Gson, keyValueStore: KeyValueStore) = LocalStorageService(
        gson,
        keyValueStore
    )
}