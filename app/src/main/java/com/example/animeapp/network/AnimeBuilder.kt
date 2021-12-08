package com.example.animeapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AnimeBuilder {
    private const val BASE_URL= "https://api.jikan.moe/v3/"
    private fun retrofit(): Retrofit= Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val animeApi: AnimeApi = retrofit().create(AnimeApi::class.java)
}