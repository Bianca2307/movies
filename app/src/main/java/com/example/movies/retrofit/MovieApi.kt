package com.example.movies.retrofit

import com.example.movies.pojo.ActorsList
import com.example.movies.pojo.GenresList
import com.example.movies.pojo.MovieList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {


    @GET("discover/movie?api_key=d773193a88ede0c03b5da21759b8dea6&language=en-US")
    fun getMovies():Call<MovieList>

    @GET("person/popular?api_key=d773193a88ede0c03b5da21759b8dea6&language=en-US")
    fun getActors():Call<ActorsList>

    @GET("genre/movie/list?api_key=d773193a88ede0c03b5da21759b8dea6&language=en-US")
    fun getGenres():Call<GenresList>

}