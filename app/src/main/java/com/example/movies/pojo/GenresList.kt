package com.example.movies.pojo

import com.google.gson.annotations.SerializedName

data class GenresList(
    @SerializedName("genres")
    val genres:List<Genre>
)
