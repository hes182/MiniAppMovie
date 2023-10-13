package com.example.cleanarchicmoview.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cleanarchicmoview.data.local.entities.MovieEntity


@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoviesFav(movies: MovieEntity)

    @Query("SELECT * FROM popularMovies")
    suspend fun getPopularMovies() : List<MovieEntity>

    @Query("DELETE FROM popularMovies")
    suspend fun deleteAllMoview()

    @Query("SELECT * FROM popularMovies WHERE id = :id")
    suspend fun getFavMovieByID(id: Long) : MovieEntity
}