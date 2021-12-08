package com.example.animeapp.data

import com.google.gson.annotations.SerializedName



data class AnimeData (
	@SerializedName("top") val top : List<Top>
)