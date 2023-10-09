package com.example.movies.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.databinding.GenreItemBinding
import com.example.movies.pojo.Genre
import com.example.movies.pojo.GenresList

class GenreAdapter:RecyclerView.Adapter<GenreAdapter.GenreViewHolder>(){

    private var genresList = ArrayList<Genre>()
    var onItemClick : ((Genre) -> Unit)? = null

    fun setGenres(genresList: List<Genre>){
        this.genresList = genresList as ArrayList<Genre>
        notifyDataSetChanged()
    }

    inner class GenreViewHolder(val binding:GenreItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder(GenreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return genresList.size
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
      holder.binding.tvGenre.text = genresList[position].name

        holder.itemView.setOnClickListener{
            onItemClick!!.invoke(genresList[position])
        }
    }
}