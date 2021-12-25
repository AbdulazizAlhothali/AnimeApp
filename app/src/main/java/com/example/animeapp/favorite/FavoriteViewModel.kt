package com.example.animeapp.favorite


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animeapp.data.firestore.Favorite
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

class FavoriteViewModel : ViewModel() {

    private val favRepo= FavoriteRepo()
    fun showMyFavAnime(favList: MutableList<Favorite>): LiveData<MutableList<Favorite>> {
        val fav = MutableLiveData<MutableList<Favorite>>()
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser!!.uid
        Log.d("CURRENTUSER",currentUser)
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
                fav.value = favList
            }
        })
        return fav
    }

    fun delete(favAnime: Favorite) {
        favRepo.deleteRepo(favAnime)
    }
}