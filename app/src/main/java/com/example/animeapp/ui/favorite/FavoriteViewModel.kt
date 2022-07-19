package com.example.animeapp.ui.favorite


import androidx.lifecycle.*
import com.example.animeapp.data.firestore.Favorite
import com.example.animeapp.data.firestore.FavoriteLiveData
import kotlinx.coroutines.flow.StateFlow

class FavoriteViewModel(private val favRepo: FavoriteRepo) : ViewModel() {


    fun showMyFavAnime2(ticker: String,newList:MutableList<Favorite>): FavoriteLiveData {
        return favRepo.showMyFavAnime2(ticker,newList)
    }

    fun delete(favAnime: Favorite) {
        favRepo.deleteRepo(favAnime)
    }
}

class FavoriteViewModelFactory (private val repo: FavoriteRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}