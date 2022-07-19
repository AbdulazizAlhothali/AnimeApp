package com.example.animeapp.ui.main

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter("loadimage")
fun bindingImage(animeImage: ImageView, imageUri: String){
    animeImage.load(imageUri)
}