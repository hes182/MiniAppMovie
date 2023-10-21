package com.example.cleanarchicmoview.di

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.cleanarchicmoview.BuildConfig
import com.example.cleanarchicmoview.data.local.DatabaseConverters
import com.example.cleanarchicmoview.data.local.MovieDao
import com.example.cleanarchicmoview.data.local.MovieDatabase
import com.example.cleanarchicmoview.data.local.MoviePopDao
import com.example.cleanarchicmoview.data.services.localStorage.KeyValueStore
import com.example.cleanarchicmoview.data.services.localStorage.SharedPreferencesKeyValueStore
import com.example.cleanarchicmoview.data.utils.GsonParser
import com.example.cleanarchicmoview.domain.mapper.MovieMapper
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.*

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideGson() = Gson()

    @Singleton
    @Provides
    fun provideSharedPreferencesKeyValueStore(preferences: SharedPreferences): KeyValueStore =
        SharedPreferencesKeyValueStore(preferences)

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences =
        appContext.getSharedPreferences(
            BuildConfig.APPLICATION_ID,
            Activity.MODE_PRIVATE
        )

    @Provides
    @Singleton
    fun provideMovieDatabase(app: Application, gson: Gson): MovieDatabase {
        return Room.databaseBuilder(app, MovieDatabase::class.java, "movie_db")
            .addTypeConverter(DatabaseConverters(GsonParser(gson)))
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDao(db: MovieDatabase): MovieDao = db.dao

    @Provides
    @Singleton
    fun provideMovieMapper(): MovieMapper = MovieMapper()

    @Provides
    @Singleton
    fun provideMovDao(db: MovieDatabase) : MoviePopDao = db.movDao
}
