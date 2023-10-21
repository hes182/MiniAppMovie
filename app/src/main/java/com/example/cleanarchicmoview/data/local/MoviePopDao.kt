package com.example.cleanarchicmoview.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cleanarchicmoview.data.local.entities.MoviePopulerEntity

@Dao
interface MoviePopDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoviePop(movies: List<MoviePopulerEntity>)

    @Query("SELECT * FROM movie")
    suspend fun getMoviePop() : List<MoviePopulerEntity>

    @Query("DELETE FROM movie")
    suspend fun deleteAllPopMovies()
}