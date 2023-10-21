package com.example.cleanarchicmoview.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cleanarchicmoview.data.local.entities.MovieEntity
import com.example.cleanarchicmoview.data.local.entities.MoviePopulerEntity

@Database(
    entities = [MovieEntity::class, MoviePopulerEntity::class],
    version = 3,
    exportSchema = false
)

@TypeConverters(DatabaseConverters::class)
abstract class MovieDatabase: RoomDatabase() {
    abstract val dao: MovieDao
    abstract val movDao: MoviePopDao
}