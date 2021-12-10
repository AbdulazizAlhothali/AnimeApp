package com.example.animeapp.network

import com.example.animeapp.data.AnimeData
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeApi {
    @GET("anime?page[limit]=20&page[offset]=0")
    suspend fun getTopAnime(): AnimeData

    @GET("anime")
    suspend fun searchAnime(@Query("filter[text]") searchKeyword: String): AnimeData
}