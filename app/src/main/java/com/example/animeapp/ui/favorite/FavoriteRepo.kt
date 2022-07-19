package com.example.animeapp.ui.favorite

import com.example.animeapp.data.firestore.Favorite
import com.example.animeapp.data.firestore.FavoriteLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

class FavoriteRepo {

    fun showMyFavAnime2(ticker : String,newList:MutableList<Favorite>):FavoriteLiveData{
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser!!.uid
        val db = FirebaseFirestore.getInstance()
        val ref =db.collection("users").document(currentUser).collection(ticker)
        return FavoriteLiveData(ref,newList)
    }

    fun deleteRepo(favAnime: Favorite) {
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser!!.uid
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(currentUser).collection("Favorite")
            .document(favAnime.id).delete()
    }


}