package com.example.animeapp.ui.main

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.animeapp.R
import com.example.animeapp.data.Data
import com.example.animeapp.data.Details
import com.example.animeapp.data.firestore.Favorite
import com.example.animeapp.databinding.RecyclerViewItemBinding
import com.example.animeapp.ui.search.SearchFragmentDirections
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

class AnimeAdapter(private val top: List<Data>,private val caller: String) : RecyclerView.Adapter<CustomHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
        val bind = DataBindingUtil.inflate<RecyclerViewItemBinding>(LayoutInflater.from(parent.context),
            R.layout.recycler_view_item,parent,false)
        return CustomHolder(bind,caller)
    }

    override fun onBindViewHolder(holder: CustomHolder, position: Int) {
        val anime = top[position]

        holder.bind(anime)

    }

    override fun getItemCount(): Int {
        return top.size
    }

}
class CustomHolder(private val binding: RecyclerViewItemBinding, private val caller: String): RecyclerView.ViewHolder(binding.root){

    private val firebaseUserId = FirebaseAuth.getInstance().currentUser!!.uid
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    @SuppressLint("UseCompatLoadingForDrawables")
    private val drawRed = binding.root.resources.getDrawable(R.drawable.ic_baseline_favorite_24, binding.root.resources.newTheme())
    @SuppressLint("UseCompatLoadingForDrawables")
    private val drawTale = binding.root.resources.getDrawable(R.drawable.ic_baseline_favorite_24, binding.root.resources.newTheme())
    lateinit var action: NavDirections
    fun bind(anime: Data){
        drawRed.setTint(binding.root.resources.getColor(R.color.red, binding.root.resources.newTheme()) )
        drawRed.setTintMode(PorterDuff.Mode.SRC_IN)
        drawTale.setTint(binding.root.resources.getColor(R.color.purple_200, binding.root.resources.newTheme()))
        drawTale.setTintMode(PorterDuff.Mode.SRC_IN)
        binding.btnLike.setImageDrawable(drawTale)
        binding.btnLike.setOnClickListener {
            check(true,anime)
        }
        check(false,anime)
        binding.ivAnimePoster.load(anime.attributes.posterImage.large)
        binding.tvAnimeName.text = anime.attributes.canonicalTitle
        binding.tvRate.text= anime.attributes.averageRating.toString()
        val description = anime.attributes.description
        val ageRate = anime.attributes.ageRating
        val animeEp= anime.attributes.episodeCount.toString()+"ep"
        binding.root.setOnClickListener {
            val detailsArg = Details(anime.attributes.canonicalTitle,
                anime.attributes.posterImage.original,
            description,
                anime.attributes.averageRating.toString(),
            ageRate,animeEp)
            when(caller){
                "AnimeFragment" -> action = MainFragmentDirections.actionMainFragmentToAnimeDetailsFragment(detailsArg)
                "SearchFragment" -> action = SearchFragmentDirections.actionSearchFragmentToAnimeDetailsFragment(detailsArg)
            }
            binding.root.findNavController().navigate(action)
        }
    }

    private fun check(onclick: Boolean, anime: Data){
        firebaseFirestore.collection("users")
            .document(firebaseUserId)
            .collection("Favorite")
            .document(anime.attributes.canonicalTitle)
            .get()
            .addOnCompleteListener {
                if (it.result.exists()){
                    if (!onclick){
                        binding.btnLike.setImageDrawable(drawRed)
                    }else {
                        firebaseFirestore.collection("users").document(firebaseUserId)
                            .collection("Favorite")
                            .document(anime.attributes.canonicalTitle).delete()
                        binding.btnLike.setImageDrawable(drawTale)
                    }
                } else {
                    if (!onclick) {
                        binding.btnLike.setImageDrawable(drawTale)
                    }else{
                        val fav= Favorite(firebaseUserId,
                            anime.attributes.posterImage.original,
                            anime.attributes.canonicalTitle,"0.0","0")
                        firebaseFirestore.collection("users").document(firebaseUserId).collection("Favorite").document(anime.attributes.canonicalTitle)
                            .set(fav)
                            .addOnSuccessListener {
                                Log.d("TAG", "DocumentSnapshot successfully written!")
                            }
                            .addOnFailureListener { e ->
                                Log.w("TAG", "Error writing document", e)
                            }
                        binding.btnLike.setImageDrawable(drawRed)
                    }
                }
            }
    }
}
