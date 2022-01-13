package com.example.animeapp.ui.search

import android.util.Log
import androidx.lifecycle.*
import com.example.animeapp.data.AnimeData
import com.example.animeapp.network.AnimeRepo
import kotlinx.coroutines.launch

class SearchViewModel() : ViewModel() {
    private val repo = AnimeRepo()
    var savedState = ""

    fun searchAnime (searchKeyword: String): LiveData<AnimeData> {
        val animes = MutableLiveData<AnimeData>()
        viewModelScope.launch {
            try {
                animes.postValue(repo.searchAnime(searchKeyword))
                savedState = searchKeyword
            } catch (e: Throwable ){
                Log.e("Anime", "Anime Problem : ${e.localizedMessage}")
            }
        }
        return animes
    }
}