package com.example.animeapp.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.animeapp.R
import com.example.animeapp.data.firestore.Favorite
import com.example.animeapp.databinding.FavoriteRecyclerviewItemBinding

class FavoriteAdapter(private val favAnime: List<Favorite>) : RecyclerView.Adapter<CustomHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
        val bind = DataBindingUtil.inflate<FavoriteRecyclerviewItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.favorite_recyclerview_item,parent,false)
        return CustomHolder(bind)
    }
    override fun onBindViewHolder(holder: CustomHolder, position: Int) {
        val anime = favAnime[position]
        holder.bind(anime)
    }
    override fun getItemCount(): Int {
        return favAnime.size
    }
}
class CustomHolder(private val binding: FavoriteRecyclerviewItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(anime: Favorite) {
        /*binding.ivAnimePoster.load(anime.posterImage)
        binding.tvAnimeName.text = anime.animeTitle
        binding.tvRate.text = anime.rate
        binding.tvEp.text = anime.episode*/
        binding.apply {
            fav = anime
            root.setOnClickListener {
                val action= FavoriteFragmentDirections.actionFavoriteFragmentToFavDetails(anime.animeTitle,anime.rate,anime.episode,anime.id)
                root.findNavController().navigate(action)
            }
        }

    }
}