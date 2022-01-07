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
        return fav
    }

    fun delete(favAnime: Favorite) {
        favRepo.deleteRepo(favAnime)
    }
}