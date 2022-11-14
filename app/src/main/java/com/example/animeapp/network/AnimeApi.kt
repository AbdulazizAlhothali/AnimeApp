package com.example.animeapp.network

import com.example.animeapp.data.AnimeData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeApi {
    @GET("anime?page[limit]=20&")
    suspend fun allAnime(@Query("page[offset]") pageNum: String): AnimeData

    @GET("anime")
    suspend fun searchAnime(@Query("filter[text]") searchKeyword: String): AnimeData

    @GET("trending/anime")
    suspend fun trendingAnime (): AnimeData
}