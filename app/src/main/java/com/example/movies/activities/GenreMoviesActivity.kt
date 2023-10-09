package com.example.movies.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.R
import com.example.movies.adapters.GenreMovieAdapter
import com.example.movies.databinding.ActivityGenreBinding
import com.example.movies.fragments.HomeFragment
import com.example.movies.fragments.HomeFragment.Companion.GENRE_ID
import com.example.movies.pojo.Movie
import com.example.movies.viewModel.HomeViewModel

class GenreMoviesActivity : AppCompatActivity() {

    private lateinit var genreId:String

     private lateinit var homeViewModel: HomeViewModel
     private lateinit var binding:ActivityGenreBinding
     private lateinit var movieGenresAdapter: GenreMovieAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)




       getGenreId()
        loadMoviesByGenre(genreId)

    }

    private fun prepareRecyclerView() {
        movieGenresAdapter = GenreMovieAdapter()
        binding.rvMovies.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL, false)
            adapter = movieGenresAdapter
        }
    }

    private fun getGenreId(){
        val intent = intent
        genreId = intent.getStringExtra(HomeFragment.GENRE_ID) ?: ""
    }
    private fun loadMoviesByGenre(genreId : String) {

        // Observe the LiveData to get the list of movies
        homeViewModel.movieList.observe(this) { movies ->
            val genreIdInt = genreId.toIntOrNull()
            Log.d("MovieActivity", "Parsed Genre ID: $genreIdInt")

            // Filter movies that contain the selected genre ID in their genre_ids array
            val filteredMovieTitles = movies.filter { movie ->
                val movieGenreIds = movie.genre_ids
                val containsGenre = movieGenreIds!!.contains(genreIdInt)
                Log.d(
                    "MovieActivity",
                    "Movie ID: ${movie.id}, Genre IDs: $movieGenreIds, Contains Genre: $containsGenre"
                ) // Log movie ID, genre IDs, and whether it contains the genre
                containsGenre
            }
                //.map { it.title } // Extract movie titles from the filtered movies

            Log.d("MovieActivity", "Genre ID: $genreId")
            Log.d("MovieActivity", "Filtered Movie Titles: $filteredMovieTitles")

            // You can update your UI with the filtered movie titles here
            binding.tvMoviesCount.text = filteredMovieTitles.size.toString()
            movieGenresAdapter.setMovieGenresList(filteredMovieTitles)
        }
    }
}