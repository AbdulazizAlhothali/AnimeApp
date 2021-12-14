package com.example.animeapp.network

import com.example.animeapp.data.AnimeData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AnimeRepo {
    private val api= AnimeBuilder.animeApi

    suspend fun allAnime(pageNum:String): AnimeData = withContext(Dispatchers.IO) {
        api.allAnime(pageNum)
    }
    suspend fun searchAnime(searchKeyword: String): AnimeData = withContext(Dispatchers.IO) {
        api.searchAnime(searchKeyword)
    }

}