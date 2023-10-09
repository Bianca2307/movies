package com.example.movies.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.db.MovieDatabase
import com.example.movies.pojo.Movie
import kotlinx.coroutines.launch

class MovieViewModel(val movieDatabase: MovieDatabase): ViewModel() {



        fun insertMovie(movie: Movie){
        viewModelScope.launch {
            movieDatabase.movieDao().upset(movie)
        }
    }




}