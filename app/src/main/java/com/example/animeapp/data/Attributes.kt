package com.example.animeapp.data

data class Attributes(
    val createdAt : String,
    val updatedAt : String,
    val slug : String,
    val synopsis : String,
    val description : String,
    val coverImageTopOffset : Int,
    val titles : Titles,
    val canonicalTitle : String,
    val averageRating : Double,
    val favoritesCount : Int,
    val startDate : String,
    val endDate : String,
    val nextRelease : String,
    val ageRating : String,
    val subtype : String,
    val status : String,
    val tba : String,
    val posterImage : PosterImage,
    val coverImage : CoverImage,
    val episodeCount : Int,
    val nsfw : Boolean
)
