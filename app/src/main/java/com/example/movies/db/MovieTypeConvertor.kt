package com.example.movies.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@TypeConverters
class MovieTypeConvertor {

    @TypeConverter
    fun fromListToString(list: List<Int>?): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToList(value: String?): List<Int>? {
        val listType = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(value, listType)
    }
}