package com.example.animeapp.data.firestore

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Favorite( var id: String = "", var posterImage: String = "", var animeTitle: String = "" ): Parcelable
