package com.example.animeapp.data.firestore

data class User (val user_id:String,val user_name:String,val user_userName :String
                 ,val user_email:String
                 ,val bio:String =" ",
                 val profile_image:String=" ")
