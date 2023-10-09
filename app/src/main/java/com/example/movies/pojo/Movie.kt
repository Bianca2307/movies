package com.example.movies.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movieInformation")
data class Movie(
    @PrimaryKey
    val id:String,
    val title:String?,
    val poster_path:String?,
    val overview:String?,
    val genre_ids: List<Int>?

)
