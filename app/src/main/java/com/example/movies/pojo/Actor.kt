package com.example.movies.pojo

data class Actor(
    val id:String,
    val name:String,
    val profile_path:String,
    val known_for: List<KnownFor>
)
