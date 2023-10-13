package com.example.cleanarchicmoview.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.cleanarchicmoview.data.local.entities.GenreEntity
import com.example.cleanarchicmoview.data.utils.JsonParser
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class DatabaseConverters(private val jsonParser: JsonParser) {

    @TypeConverter
    fun fromGenresJson(json: String) : List<GenreEntity> {
        return jsonParser.fromJson<ArrayList<GenreEntity>>(
            json,
            object : TypeToken<ArrayList<GenreEntity>>() {}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toGenresJson(movies: List<GenreEntity>?) : String {
        return jsonParser.toJson(
            movies,
            object : TypeToken<ArrayList<GenreEntity>>() {}.type
        ) ?: "[]"
    }
}