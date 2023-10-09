package com.example.movies.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.activities.GenreMoviesActivity
import com.example.movies.activities.MainActivity
import com.example.movies.activities.MovieActivity
import com.example.movies.adapters.ActorsAdapter
import com.example.movies.adapters.GenreAdapter
import com.example.movies.adapters.MovieClickInterface
import com.example.movies.adapters.MovieListAdapter
import com.example.movies.databinding.FragmentHomeBinding
import com.example.movies.pojo.ActorWithKnownFor
import com.example.movies.pojo.Movie
import com.example.movies.viewModel.HomeViewModel


class HomeFragment : Fragment(), MovieClickInterface {

    private lateinit var binding:FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var movieListAdapter:MovieListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var movie:Movie
   private lateinit var popularActorsAdapter:ActorsAdapter
   private lateinit var genresAdapter:GenreAdapter

    companion object {
        const val MOVIE_ID = "com.example.movies.fragments.movieId"
        const val MOVIE_TITLE = "com.example.movies.fragments.movieTitle"
        const val MOVIE_DESCRIPTION = "com.example.movies.fragments.movieOverview"
        const val MOVIE_IMG = "com.example.movies.fragments.movieImg"
        const val GENRE_ID = "com.example.movies.fragments.genreId"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        popularActorsAdapter = ActorsAdapter()
        genresAdapter = GenreAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerView

        viewModel = (activity as MainActivity).viewModel

        movieListAdapter = MovieListAdapter(requireContext(), (viewModel.movieList.value ?: emptyList()).toMutableList(), this)
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
        recyclerView.adapter = movieListAdapter
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularActorsRecyclerView()
        prepareGenresRecyclerView()

        viewModel.movieList.observe(viewLifecycleOwner){
            movieListAdapter.updateData(it)
        }

        viewModel.actorsList.observe(viewLifecycleOwner){
            actorsList -> popularActorsAdapter.setActors(actorsList = actorsList as ArrayList<ActorWithKnownFor>)
        }

        viewModel.genresList.observe(viewLifecycleOwner){
            genresList -> genresAdapter.setGenres(genresList)
        }
        onPopularActorsClick()
        onGenreClick()

    }

    private fun onGenreClick() {
        genresAdapter.onItemClick = {genre ->
            val intent = Intent(activity, GenreMoviesActivity::class.java)
            intent.putExtra(GENRE_ID, genre.id)
            startActivity(intent)

        }
    }

    private fun prepareGenresRecyclerView() {
        binding.recViewGenres.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL, false)
            adapter = genresAdapter
        }
    }

    private fun onPopularActorsClick() {
        popularActorsAdapter.onItemClick = { actorWithKnownFor->
            val intent = Intent(activity, MovieActivity::class.java)
            intent.putExtra(MOVIE_ID, actorWithKnownFor.actor.id)
            intent.putExtra(MOVIE_TITLE, actorWithKnownFor.knownFor[0].title)
           intent.putExtra(MOVIE_IMG, actorWithKnownFor.knownFor[0].poster_path)
            intent.putExtra(MOVIE_DESCRIPTION, actorWithKnownFor.knownFor[0].overview)
            startActivity(intent)
        }
    }

    private fun preparePopularActorsRecyclerView() {
        binding.rvActors.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularActorsAdapter
        }
    }

    override fun onMovieClick(movie: Movie) {
        this.movie = movie
        val intent = Intent(activity, MovieActivity::class.java)
        intent.putExtra(MOVIE_ID,movie.id)
        intent.putExtra(MOVIE_TITLE, movie.title)
        intent.putExtra(MOVIE_DESCRIPTION, movie.overview)
        intent.putExtra(MOVIE_IMG, movie.poster_path)
        startActivity(intent)
    }
}