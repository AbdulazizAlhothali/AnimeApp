package com.example.animeapp.network

import com.example.animeapp.data.AnimeData
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeApi {
    @GET("top/anime")
    suspend fun getTopAnime(): AnimeData

    @GET("search/anime")
    suspend fun searchAnime(@Query("q") searchKeyword: String): AnimeData
}