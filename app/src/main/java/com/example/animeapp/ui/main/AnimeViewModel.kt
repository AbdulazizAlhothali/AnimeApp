package com.example.animeapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.data.AnimeData
import com.example.animeapp.network.AnimeRepo
import kotlinx.coroutines.launch

class AnimeViewModel : ViewModel() {
    val repo = AnimeRepo()

    fun anime (searchKeyword: String?): LiveData<AnimeData>{
        val animes = MutableLiveData<AnimeData>()
        viewModelScope.launch {
            try {
                if (searchKeyword.isNullOrBlank()){
                    animes.postValue(repo.getTopAnime())
                } else {
                    animes.postValue(repo.searchAnime(searchKeyword))
                }
            } catch (e: Throwable ){
                Log.e("Anime", "Anime Problem : ${e.localizedMessage}")
            }
        }
        return animes
    }
}