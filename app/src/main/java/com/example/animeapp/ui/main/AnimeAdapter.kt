package com.example.animeapp.ui.main

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
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

class AnimeAdapter(private val top: List<Data>, private val caller: String) :
    RecyclerView.Adapter<CustomHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
        val bind = DataBindingUtil.inflate<RecyclerViewItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.recycler_view_item, parent, false
        )
        return CustomHolder(bind, caller)
    }

    override fun onBindViewHolder(holder: CustomHolder, position: Int) {
        val anime = top[position]
        holder.bind(anime)
    }

    override fun getItemCount(): Int {
        return top.size
    }

}

class CustomHolder(private val binding: RecyclerViewItemBinding, private val caller: String) :
    RecyclerView.ViewHolder(binding.root) {

    private val firebaseUserId = FirebaseAuth.getInstance().currentUser!!.uid
    private val firebaseFirestore = FirebaseFirestore.getInstance()

    @SuppressLint("UseCompatLoadingForDrawables")
    private val drawRed = binding.root.resources.getDrawable(
        R.drawable.ic_baseline_favorite_24,
        binding.root.resources.newTheme()
    )

    @SuppressLint("UseCompatLoadingForDrawables")
    private val drawTale = binding.root.resources.getDrawable(
        R.drawable.ic_baseline_favorite_24,
        binding.root.resources.newTheme()
    )
    lateinit var action: NavDirections
    fun bind(anime: Data) {

        binding.apply {
            drawRed.setTint(root.resources.getColor(R.color.red, root.resources.newTheme()))
            drawRed.setTintMode(PorterDuff.Mode.SRC_IN)
            drawTale.setTint(root.resources.getColor(R.color.purple_200, root.resources.newTheme()))
            drawTale.setTintMode(PorterDuff.Mode.SRC_IN)
            btnLike.setImageDrawable(drawTale)
            btnLike.setOnClickListener {
                if (btnLike.drawable == drawRed) {
                    firebaseFirestore.collection("users").document(firebaseUserId)
                        .collection("Favorite")
                        .document(anime.id.toString()).delete()
                    btnLike.setImageDrawable(drawTale)
                } else {
                    val fav = Favorite(
                        anime.id.toString(),
                        anime.attributes.posterImage.original,
                        anime.attributes.canonicalTitle, "0.0", "0"
                    )
                    firebaseFirestore.collection("users").document(firebaseUserId)
                        .collection("Favorite").document(anime.id.toString())
                        .set(fav)
                    btnLike.setImageDrawable(drawRed)
                }
            }

            /*ivAnimePoster.load(anime.attributes.posterImage.large)
            tvAnimeName.text = anime.attributes.titles.en
            tvRate.text = anime.attributes.averageRating.toString()*/
            myData = anime
            val description = anime.attributes.description
            val ageRate: String? = anime.attributes.ageRating
            val animeEp = anime.attributes.episodeCount.toString() + "ep"
            check(anime)
            root.setOnClickListener {
                val detailsArg = Details(
                    anime.attributes.canonicalTitle,
                    anime.attributes.posterImage.large,
                    description,
                    anime.attributes.averageRating.toString(),
                    ageRate ?: "no rate", animeEp
                )
                when (caller) {
                    "AnimeFragment" -> action =
                        MainFragmentDirections.actionMainFragmentToAnimeDetailsFragment(detailsArg)
                    "SearchFragment" -> action =
                        SearchFragmentDirections.actionSearchFragmentToAnimeDetailsFragment(
                            detailsArg
                        )
                }
                root.findNavController().navigate(action)
            }
        }

    }

    private fun check(anime: Data) {
        firebaseFirestore.collection("users")
            .document(firebaseUserId)
            .collection("Favorite")
            .document(anime.id.toString())
            .get()
            .addOnCompleteListener {
                if (it.result.exists()) {
                    binding.btnLike.setImageDrawable(drawRed)
                } else {
                    binding.btnLike.setImageDrawable(drawTale)
                }
            }
    }
}
