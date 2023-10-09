package com.example.movies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.databinding.FragmentFavoritesBinding
import com.example.movies.databinding.MovieItemBinding
import com.example.movies.pojo.Movie

class FavoriteMoviesAdapter:RecyclerView.Adapter<FavoriteMoviesAdapter.FavoriteMovieAdapterViewHolder>() {

    inner class FavoriteMovieAdapterViewHolder(val binding: MovieItemBinding):RecyclerView.ViewHolder(binding.root)

    /** Basically diffUtil is a class that you can attach to your recycler
     * view to improve performance
     * It works asynchronous that means it won't affect the recycler view
     */

    private val diffUtil = object : DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteMovieAdapterViewHolder {
        return FavoriteMovieAdapterViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FavoriteMovieAdapterViewHolder, position: Int) {
        val movie = differ.currentList[position]
        val poster_path = movie.poster_path
        val imgUrl = "https://image.tmdb.org/t/p/w200$poster_path"
        Glide.with(holder.itemView).load(imgUrl).into(holder.binding.imgMovie)
        holder.binding.tvMovie.text = movie.title
    }


}