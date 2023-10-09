package com.example.movies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.databinding.PopularActorsBinding
import com.example.movies.pojo.Actor
import com.example.movies.pojo.ActorWithKnownFor
import com.example.movies.pojo.ActorsList

class ActorsAdapter:RecyclerView.Adapter<ActorsAdapter.ActorsViewHolder>() {

    lateinit var onItemClick:((ActorWithKnownFor) -> Unit)
    private var actorsList = ArrayList<ActorWithKnownFor>()

    fun setActors(actorsList:ArrayList<ActorWithKnownFor>){
        this.actorsList = actorsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorsViewHolder {
        return ActorsViewHolder(PopularActorsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ActorsViewHolder, position: Int) {
        val poster_path = actorsList[position].actor.profile_path
        val imgUrl = "https://image.tmdb.org/t/p/w200$poster_path"


        Glide.with(holder.itemView).load(imgUrl).into(holder.binding.imgPopularActors)

        holder.itemView.setOnClickListener{
            onItemClick.invoke(actorsList[position])
        }
    }

    override fun getItemCount(): Int {
       return actorsList.size
    }

    class ActorsViewHolder(val binding:PopularActorsBinding):RecyclerView.ViewHolder(binding.root)
}