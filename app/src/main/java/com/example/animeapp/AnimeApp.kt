package com.example.animeapp

import android.app.Application
import com.example.animeapp.ui.favorite.FavoriteRepo

class AnimeApp: Application() {
    val favRepo by lazy { FavoriteRepo() }
}