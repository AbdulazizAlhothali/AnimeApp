package com.example.animeapp.ui.favorite


import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animeapp.data.firestore.Favorite

class FavoriteViewModel : ViewModel() {

    private val favRepo= FavoriteRepo()
    fun showMyFavAnime(newList:MutableList<Favorite>, viewLifecycleOwner: LifecycleOwner): LiveData<MutableList<Favorite>> {
        val fav = MutableLiveData<MutableList<Favorite>>()
        favRepo.showMyFavAnime(newList).observe(viewLifecycleOwner,{
            fav.postValue(it)
        })

        /*val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser!!.uid
        Log.d("CURRENTUSER",currentUser)
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(currentUser).collection("Favorite").addSnapshotListener(object:
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
        })*/
        return fav
    }

    fun delete(favAnime: Favorite) {
        favRepo.deleteRepo(favAnime)
    }
}