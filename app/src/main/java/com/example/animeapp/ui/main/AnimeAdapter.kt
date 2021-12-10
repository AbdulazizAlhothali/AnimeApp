package com.example.animeapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.animeapp.R
import com.example.animeapp.data.AnimeData
import com.example.animeapp.data.Data

import com.example.animeapp.databinding.RecyclerViewItemBinding

class AnimeAdapter(val top: List<Data>) : RecyclerView.Adapter<CustomHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
        val bind = DataBindingUtil.inflate<RecyclerViewItemBinding>(LayoutInflater.from(parent.context),
            R.layout.recycler_view_item,parent,false)
        return CustomHolder(bind)
    }

    override fun onBindViewHolder(holder: CustomHolder, position: Int) {
        val anime = top[position]
        holder.bind(anime)
    }

    override fun getItemCount(): Int {
        return top.size
    }

}
class CustomHolder(private val binding: RecyclerViewItemBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(anime: Data){

        binding.ivAnimePoster.load(anime.attributes.posterImage.small)
        binding.tvAnimeName.text = anime.attributes.canonicalTitle
        binding.tvRate.text= anime.attributes.averageRating.toString()
    }
}
