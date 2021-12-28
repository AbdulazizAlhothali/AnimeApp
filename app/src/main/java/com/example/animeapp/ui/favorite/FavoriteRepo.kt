package com.example.animeapp.ui.favorite

import android.util.Log
import com.example.animeapp.data.firestore.Favorite
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

class FavoriteRepo {

    fun showMyFavAnime(): List<Favorite> {
        val favList: MutableList<Favorite> = mutableListOf()
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser!!.uid
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(currentUser).collection("Favorite").addSnapshotListener(object :
            EventListener<QuerySnapshot> {

            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {

                if (error != null) {
                    Log.e("Firestore", error.message.toString())
                    return
                }
                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        favList.add(dc.document.toObject(Favorite::class.java))
                    }
                }
            }

        })
        return favList
    }

    fun deleteRepo(favAnime: Favorite) {

        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser!!.uid
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(currentUser).collection("Favorite")
            .document(favAnime.animeTitle).delete()
    }
}