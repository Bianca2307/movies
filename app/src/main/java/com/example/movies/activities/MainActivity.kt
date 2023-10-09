package com.example.movies.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.movies.R
import com.example.movies.db.MovieDatabase
import com.example.movies.viewModel.HomeViewModel
import com.example.movies.viewModel.HomeViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

     val viewModel:HomeViewModel by lazy{
         val movieDatabase = MovieDatabase.getInstance(this)
         val homeViewModelProviderFactory = HomeViewModelFactory(movieDatabase)
         ViewModelProvider(this, homeViewModelProviderFactory).get(HomeViewModel::class.java)
     }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.btm_navigation)
        val navController = Navigation.findNavController(this, R.id.myNavHostFragment)

        NavigationUI.setupWithNavController(bottomNavigation, navController)
    }
}