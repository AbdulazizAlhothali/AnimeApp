package com.example.animeapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.animeapp.R
import com.example.animeapp.data.Data
import com.example.animeapp.data.Details
import com.example.animeapp.databinding.SearchItemBinding
import com.example.animeapp.ui.main.AnimeFragmentDirections

class SearchAnimeAdapter(val top: List<Data>) : RecyclerView.Adapter<CustomHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
        val bind = DataBindingUtil.inflate<SearchItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.search_item,parent,false)
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
class CustomHolder(private val binding: SearchItemBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(anime: Data){

        binding.ivAnimePoster.load(anime.attributes.posterImage.original)
        binding.tvAnimeName.text = anime.attributes.canonicalTitle
        binding.tvRate.text= anime.attributes.averageRating.toString()
        val description = anime.attributes.description
        val ageRate = anime.attributes.ageRating

        binding.root.setOnClickListener {
            Toast.makeText(binding.root.context,"you clicked ${binding.tvAnimeName.text}", Toast.LENGTH_LONG).show()


            val detailsArg = Details(anime.attributes.canonicalTitle,
                anime.attributes.posterImage.original,
                description,
                anime.attributes.averageRating.toString(),
                ageRate)
            val action= SearchFragmentDirections.actionSearchFragmentToAnimeDetailsFragment(
                detailsArg
            )
            binding.root.findNavController().navigate(action)

            /*** saving data to Firestore */
            /* val firebaseUserId = FirebaseAuth.getInstance().currentUser!!.uid
            val fav= Favorite(firebaseUserId,anime.attributes.posterImage.original,binding.tvAnimeName.text.toString())
            val firebaseFirestore = FirebaseFirestore.getInstance()
            firebaseFirestore.collection("users").document(firebaseUserId).collection("Favorite").document("${binding.tvAnimeName.text}")
                .set(fav)
                .addOnSuccessListener {
                    Log.d("TAG", "DocumentSnapshot successfully written!")
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error writing document", e)
                }*/


        }
    }
}