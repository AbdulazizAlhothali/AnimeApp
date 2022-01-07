package com.example.animeapp.ui.favorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.animeapp.data.firestore.Favorite
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

class FavoriteRepo {

    fun showMyFavAnime(newList:MutableList<Favorite>): LiveData<MutableList<Favorite>> {
        val favList= MutableLiveData<MutableList<Favorite>>()
        favList.value= mutableListOf()
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
                        newList.add(dc.document.toObject(Favorite::class.java))
                    }
                }
                favList.value= newList
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