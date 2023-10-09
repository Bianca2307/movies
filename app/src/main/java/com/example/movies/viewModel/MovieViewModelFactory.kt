package com.example.movies.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movies.db.MovieDatabase

class MovieViewModelFactory(
    private val movieDatabase: MovieDatabase
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieViewModel(movieDatabase) as T
    }
}