package com.example.animeapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Details(val title: String, val poster : String, val desc: String, val rate : String, val ageRate: String): Parcelable
