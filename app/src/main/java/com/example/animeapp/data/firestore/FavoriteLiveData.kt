package com.example.animeapp.data.firestore

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.firestore.*

class FavoriteLiveData(private val collectionReference: CollectionReference,private val newList: MutableList<Favorite>):
LiveData<List<Favorite>>(), EventListener<QuerySnapshot>{
    private var listenerRegistration: ListenerRegistration? = null
    override fun onActive() {
        listenerRegistration = collectionReference.addSnapshotListener(this)
    }
    override fun onInactive() {
        listenerRegistration?.remove()
    }
    override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {


        if (error != null) {
            Log.e("Firestore", error.message.toString())
            return
        }
        value?.documentChanges?.forEach { documentChange: DocumentChange ->
            when(documentChange.type){
                DocumentChange.Type.ADDED -> newList.add(documentChange.document.toObject(Favorite::class.java))
                else -> newList.remove(documentChange.document.toObject(Favorite::class.java))
                //DocumentChange.Type.MODIFIED -> newList.add(documentChange.document.toObject(Favorite::class.java))
            }
        }

        setValue(newList)
    }


}