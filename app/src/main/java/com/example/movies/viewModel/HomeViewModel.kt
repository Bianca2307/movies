package com.example.movies.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.example.movies.db.MovieDatabase
import com.example.movies.pojo.Actor
import com.example.movies.pojo.ActorWithKnownFor
import com.example.movies.pojo.ActorsList
import com.example.movies.pojo.Genre
import com.example.movies.pojo.GenresList
import com.example.movies.pojo.Movie
import com.example.movies.pojo.MovieList
import com.example.movies.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val movieDatabase: MovieDatabase): ViewModel() {

    private val _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>> = _movieList

    private val _actorsList = MutableLiveData<List<ActorWithKnownFor>>()
    val actorsList:LiveData<List<ActorWithKnownFor>> = _actorsList

    private val _genresList = MutableLiveData<List<Genre>>()
    val genresList:LiveData<List<Genre>> = _genresList

    private var favoriteMoviesLiveData = movieDatabase.movieDao().getAllMovies()





    init {
        loadMoviesList()
        loadActors()
        loadGenres()
    }

    fun loadMoviesList() {

        RetrofitInstance.api.getMovies().enqueue(object : Callback<MovieList> {
            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                if (response.isSuccessful) {

                    val movieListResponse: MovieList = response.body() !!

                    if (movieListResponse != null) {
                        _movieList.value = movieListResponse.results.shuffled()
                        Log.d("HomeViewModel", "Received ${movieListResponse.results.size} movies")

                        for(movie in movieListResponse.results.shuffled()){
                            val genreIdsList = movie.genre_ids
                            Log.d("HomeViewModel", "${genreIdsList}")

                        }

                    } else {
                        Log.e("HomeViewModel", "Movie list is null")
                    }

                } else {
                    Log.e("HomeViewModel", "Failed to fetch movies: ${response.message()}")

                }
            }

            override fun onFailure(call: Call<MovieList>, t: Throwable) {
                Log.e("HomeViewModel", "API call failed: ${t.message}")
            }
        })
    }

    fun loadActors(){
        RetrofitInstance.api.getActors().enqueue(object:Callback<ActorsList>{
            override fun onResponse(call: Call<ActorsList>, response: Response<ActorsList>) {
                if(response.isSuccessful) {
                    val actorsListResponse:ActorsList = response.body() !!

                    if(actorsListResponse != null){

                        val actorsWithKnownFor = mutableListOf<ActorWithKnownFor>()

                        for(actor in actorsListResponse.actors.shuffled()){
                            val knownForList = actor.known_for
                            val actorWithKnownFor = ActorWithKnownFor(actor, knownForList)
                            actorsWithKnownFor.add(actorWithKnownFor)

                        }

                        _actorsList.value = actorsWithKnownFor
                        Log.d("HomeViewModel", "Received ${actorsWithKnownFor.size} actors")
                    }else {
                        Log.e("HomeViewModel", "Actors list is null")
                    }

                }
            }

            override fun onFailure(call: Call<ActorsList>, t: Throwable) {
                Log.d("HomeViewModel", "API call failed:  ${t.message}")
            }
        })
    }

    fun loadGenres(){
        RetrofitInstance.api.getGenres().enqueue(object:Callback<GenresList>{
            override fun onResponse(call: Call<GenresList>, response: Response<GenresList>) {
                if(response.isSuccessful){
                    val genresListResponse:GenresList = response.body() !!

                    if(genresListResponse != null){
                        _genresList.value = genresListResponse.genres.shuffled()
                        Log.d("HomeViewModel", "Received ${genresListResponse.genres.size} genres")
                    }else {
                        Log.e("HomeViewModel", "Genres list is null")
                    }
                } else {
                    Log.e("HomeViewModel", "Failed to fetch genres: ${response.message()}")

                }
            }

            override fun onFailure(call: Call<GenresList>, t: Throwable) {
                Log.d("HomeViewModel", "API call failed:  ${t.message}")
            }
        })
    }

    fun insertMovie(movie: Movie){
        viewModelScope.launch {
            movieDatabase.movieDao().upset(movie)
        }
    }

    fun deleteMovie(movie: Movie){
        viewModelScope.launch {
            movieDatabase.movieDao().delete(movie)
        }
    }

    fun observeFavoritesMoviesLiveData(): LiveData<List<Movie>>{
        return favoriteMoviesLiveData
    }

}



