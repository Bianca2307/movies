package com.example.movies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.databinding.MovieItemBinding
import com.example.movies.pojo.GenresList
import com.example.movies.pojo.Movie

class GenreMovieAdapter:RecyclerView.Adapter<GenreMovieAdapter.ViewHolder>() {

    private var moviesGenresList = ArrayList<Movie>()

    fun setMovieGenresList(moviesGenresList: List<Movie>){
        this.moviesGenresList = moviesGenresList as ArrayList<Movie>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val poster_path = moviesGenresList[position].poster_path
        val imgUrl = "https://image.tmdb.org/t/p/w200$poster_path"
        Glide.with(holder.itemView).load(imgUrl).into(holder.binding.imgMovie)

        holder.binding.tvMovie.text = moviesGenresList[position].title
    }

    override fun getItemCount(): Int {
       return moviesGenresList.size
    }

    inner class ViewHolder(val binding:MovieItemBinding):RecyclerView.ViewHolder(binding.root)
}