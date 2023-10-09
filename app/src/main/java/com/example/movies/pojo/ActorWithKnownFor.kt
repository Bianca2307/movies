package com.example.movies.pojo

import com.google.gson.annotations.SerializedName

data class ActorWithKnownFor(
    val actor: Actor,
    val knownFor: List<KnownFor>
)
