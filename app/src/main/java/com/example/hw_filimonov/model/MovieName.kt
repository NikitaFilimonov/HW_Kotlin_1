package com.example.hw_filimonov.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieName(
    val movieName: String,
    val year: Int
) : Parcelable
