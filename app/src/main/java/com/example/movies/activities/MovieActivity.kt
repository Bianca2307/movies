package com.example.movies.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.movies.databinding.ActivityMovieBinding
import com.example.movies.db.MovieDatabase
import com.example.movies.fragments.HomeFragment
import com.example.movies.pojo.Movie
import com.example.movies.viewModel.MovieViewModel
import com.example.movies.viewModel.MovieViewModelFactory

class MovieActivity : AppCompatActivity() {

    private lateinit var movieId:String
    private lateinit var movieTitle:String
    private lateinit var moviePoster:String
    private lateinit var movieOverview:String

    private lateinit var binding:ActivityMovieBinding
    private lateinit var movieViewModel: MovieViewModel

    private var movieToSave:Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieDatabase = MovieDatabase.getInstance(this)
        val viewModelFactory = MovieViewModelFactory(movieDatabase)
        movieViewModel = ViewModelProvider(this, viewModelFactory).get(MovieViewModel::class.java)



        getMovieDetails()

        setDetailsInViews()
        onFavoriteClick()
    }

    private fun onFavoriteClick() {
        binding.addToFavorite.setOnClickListener {
            movieToSave?.let {
                movieViewModel.insertMovie(it)
                Toast.makeText(this,"Movie Saved!", Toast.LENGTH_LONG).show()
            }
        }
    }



    private fun setDetailsInViews() {
        val imageUrl = "https://image.tmdb.org/t/p/w200$moviePoster"

        Glide.with(applicationContext).load(imageUrl).into(binding.imgMovieDetail)

        binding.collapsingToolbar.title = movieTitle
        binding.tvAllDescription.text = movieOverview
    }

    private fun getMovieDetails(){
        val intent = intent
        movieId = intent.getStringExtra(HomeFragment.MOVIE_ID) ?: ""
        movieTitle = intent.getStringExtra(HomeFragment.MOVIE_TITLE) ?: ""
        moviePoster = intent.getStringExtra(HomeFragment.MOVIE_IMG) ?: ""
        movieOverview = intent.getStringExtra(HomeFragment.MOVIE_DESCRIPTION) ?: ""

        // Create a Movie object with the movie details
        movieToSave = Movie(movieId, movieTitle, moviePoster, movieOverview,null)

        Log.d("MovieActivity", "Movie ID: $movieId")
        Log.d("MovieActivity", "Movie Title: $movieTitle")
        Log.d("MovieActivity", "Movie Poster: $moviePoster")
        Log.d("MovieActivity", "Movie Overview: $movieOverview")
    }
}


