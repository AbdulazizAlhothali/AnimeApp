package com.example.animeapp.data

data class Attributes(
    val slug : String,
    val synopsis : String,
    val description : String,
    val coverImageTopOffset : Int,
    val titles : Titles,
    val canonicalTitle : String,
    val averageRating : Double,
    val ageRating : String?,
    val status : String,
    val posterImage : PosterImage,
    val episodeCount : Int,

)
