package com.example.movies.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.pojo.Movie


@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun upset(movie:Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Query("SELECT * FROM movieInformation")
    fun getAllMovies():LiveData<List<Movie>>

}