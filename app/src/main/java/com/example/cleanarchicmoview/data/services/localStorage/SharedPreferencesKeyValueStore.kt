package com.example.cleanarchicmoview.data.services.localStorage

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferencesKeyValueStore @Inject constructor(
    private val prefenrences: SharedPreferences
) : KeyValueStore {
    private var editor: SharedPreferences.Editor = prefenrences.edit()

    override fun getBool(key: String): Boolean? {
        return when (containsKey(key)) {
            true -> prefenrences.getBoolean(key, false)
            false-> null
        }
    }

    override fun setBool(key: String, value: Boolean) {
        editor.putBoolean(key, value).commit()
    }

    override fun getFloat(key: String): Float? {
        return when (containsKey(key)) {
            true -> prefenrences.getFloat(key, 0f)
            false -> null
        }
    }

    override fun setFloat(key: String, value: Float) {
        editor.putFloat(key, value).commit()
    }

    override fun getDouble(key: String): Double? {
        return when (containsKey(key)) {
            true -> prefenrences.getString(key, "0")?.toDouble()
            false -> null
        }
    }

    override fun setDouble(key: String, value: Double) {
        editor.putString(key, value.toString()).commit()
    }

    override fun getLong(key: String): Long? {
        return when(containsKey(key)) {
            true -> prefenrences.getLong(key, 0)
            false -> null
        }
    }

    override fun setLong(key: String, value: Long) {
        editor.putLong(key, value).commit()
    }

    override fun getInt(key: String): Int? {
        return when (containsKey(key)) {
            true -> prefenrences.getInt(key, 0)
            false -> null
        }
    }

    override fun setInt(key: String, value: Int) {
        editor.putInt(key,value).commit()
    }

    override fun getString(key: String): String? {
        return when(containsKey(key)) {
            true -> prefenrences.getString(key, "")
            false -> null
        }
    }

    override fun setString(key: String, value: String) {
        editor.putString(key, value).commit()
    }

    override fun remove(key: String) {
        editor.remove(key)
    }

    override fun containsKey(key: String): Boolean = prefenrences.contains(key)

    override fun clear(): Boolean = editor.clear().commit()
}