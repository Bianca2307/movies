package com.example.movies.pojo

import com.google.gson.annotations.SerializedName

data class ActorsList(
    @SerializedName("results")
    val actors:List<Actor>
)
