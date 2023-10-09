package com.example.movies.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.databinding.ItemViewBinding
import com.example.movies.pojo.Movie

class MovieListAdapter(private val context: Context, private val movieList:MutableList<Movie>, val movieClickInterface:MovieClickInterface):RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>()
{
    inner class MovieViewHolder(private val binding:ItemViewBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(movie: Movie){
            binding.tvTitle.text = movie.title
            binding.tvOverview.text = movie.overview

            val profilePath = movie.poster_path
            val imageUrl = "https://image.tmdb.org/t/p/w200$profilePath"

            Log.d("MovieListAdapter", "Movie title: ${movie.title}")
            Log.d("MovieListAdapter", "Image URL: $imageUrl")

            Glide.with(context)
                .load(imageUrl)
                .into(binding.imgMovie)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
       val movie = movieList[position]
        holder.bind(movie)

        holder.itemView.setOnClickListener{
            movieClickInterface?.onMovieClick(movie)
        }
    }

    fun updateData(newData:List<Movie>){
        movieList.clear()
        movieList.addAll(newData)
        notifyDataSetChanged()
    }
}

interface MovieClickInterface {
    fun onMovieClick(movie: Movie)

}
