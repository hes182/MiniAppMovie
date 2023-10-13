package com.example.cleanarchicmoview.di

import com.example.cleanarchicmoview.data.repositories.MovieRepositoryImpl
import com.example.cleanarchicmoview.domain.repositories.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        movieRepositoryImpl: MovieRepositoryImpl
    ) : MovieRepository
}