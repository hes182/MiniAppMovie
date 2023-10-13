package com.example.cleanarchicmoview.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cleanarchicmoview.data.local.entities.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 2,
    exportSchema = false
)

@TypeConverters(DatabaseConverters::class)
abstract class MovieDatabase: RoomDatabase() {
    abstract val dao: MovieDao
}