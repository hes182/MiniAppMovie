package com.example.cleanarchicmoview.di

import com.example.cleanarchicmoview.domain.use_case.favmovie_detail.FavMovieDetailUseCase
import com.example.cleanarchicmoview.domain.use_case.favmovie_detail.FavMovieDetailUseCaseImpl
import com.example.cleanarchicmoview.domain.use_case.favoritemovie.FavoriteMovieUseCaseImpl
import com.example.cleanarchicmoview.domain.use_case.favoritemovie.FavoriteMoviesUseCase
import com.example.cleanarchicmoview.domain.use_case.home.GetPopularMovieUseCase
import com.example.cleanarchicmoview.domain.use_case.home.GetPopularMoviesUseCaseImpl
import com.example.cleanarchicmoview.domain.use_case.movie_detail.GetMovieDetailUseCase
import com.example.cleanarchicmoview.domain.use_case.movie_detail.GetMovieDetailUseCaseImpl
import com.example.cleanarchicmoview.domain.use_case.searchmovie.SearchMovieUseCase
import com.example.cleanarchicmoview.domain.use_case.searchmovie.SearchMovieUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    @Singleton
    abstract fun bindGetPopularMovieUseCase(
        getPopularMovieUseCaseImpl: GetPopularMoviesUseCaseImpl
    ) : GetPopularMovieUseCase


    @Binds
    @Singleton
    abstract fun bindGetMovieDetailUseCase(
        getMovieDetailUseCaseImpl: GetMovieDetailUseCaseImpl
    ): GetMovieDetailUseCase

    @Binds
    @Singleton
    abstract fun bindGetMovieFavUseCase(
        getFavoriteMovieUseCaseImpl: FavoriteMovieUseCaseImpl
    ) : FavoriteMoviesUseCase

    @Binds
    @Singleton
    abstract fun bindGetDetailMovieFavUseCase(
        getFavMovieDetaulUseCaseImpl : FavMovieDetailUseCaseImpl
    ) : FavMovieDetailUseCase

    @Binds
    @Singleton
    abstract fun bindSearchMovieUseCase(
        searchMovieUseCaseImpl: SearchMovieUseCaseImpl
    ) : SearchMovieUseCase
}