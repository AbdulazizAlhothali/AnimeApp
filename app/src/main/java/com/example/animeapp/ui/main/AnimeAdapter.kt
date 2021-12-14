package com.example.animeapp.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
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
import com.example.animeapp.data.firestore.Favorite

import com.example.animeapp.databinding.RecyclerViewItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

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



    @SuppressLint("ResourceAsColor")
    fun bind(anime: Data){




        binding.btnLike.setOnClickListener {

            val myPref2 = binding.root.context.getSharedPreferences("myPref", Context.MODE_PRIVATE)
            val animeTitle = myPref2.getString("animeTitle", "")
            val editor = myPref2.edit()
            val firebaseUserId = FirebaseAuth.getInstance().currentUser!!.uid
            val firebaseFirestore = FirebaseFirestore.getInstance()
            /*** saving data to Firestore */
            if ("${binding.tvAnimeName.text}"!=animeTitle){
                editor.putString("animeTitle", "${binding.tvAnimeName.text}")
                editor.apply()
                val fav= Favorite(firebaseUserId,anime.attributes.posterImage.original,binding.tvAnimeName.text.toString())
                firebaseFirestore.collection("users").document(firebaseUserId).collection("Favorite").document("${binding.tvAnimeName.text}")
                    .set(fav)
                    .addOnSuccessListener {
                        Log.d("TAG", "DocumentSnapshot successfully written!")
                    }
                    .addOnFailureListener { e ->
                        Log.w("TAG", "Error writing document", e)
                    }
            } else{
                firebaseFirestore.collection("users").document(firebaseUserId).collection("Favorite").document("${binding.tvAnimeName.text}").delete()
                editor.putString("animeTitle", "")
                editor.apply()
            }
        }


        binding.ivAnimePoster.load(anime.attributes.posterImage.original)
        binding.tvAnimeName.text = anime.attributes.canonicalTitle
        binding.tvRate.text= anime.attributes.averageRating.toString()
        val description = anime.attributes.description
        val ageRate = anime.attributes.ageRating

        binding.root.setOnClickListener {
            Toast.makeText(binding.root.context,"you clicked ${binding.tvAnimeName.text}",Toast.LENGTH_LONG).show()


            val detailsArg = Details(anime.attributes.canonicalTitle,
                anime.attributes.posterImage.original,
            description,
                anime.attributes.averageRating.toString(),
            ageRate)
            val action= AnimeFragmentDirections.actionAnimeFragmentToAnimeDetailsFragment(
                detailsArg
            )
            binding.root.findNavController().navigate(action)



        }
    }
}
