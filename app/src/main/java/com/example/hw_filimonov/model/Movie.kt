package com.example.hw_filimonov.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val movieName: MovieName = getDefaultMovieName(),
    val rating: Int = 0
) : Parcelable

fun getDefaultMovieName() = MovieName("Movie1", 2021)

fun getWorldMovies() = listOf(
    Movie(MovieName("Английский фильм", 2020), 10),
    Movie(MovieName("Английский фильм", 2020), 1),
    Movie(MovieName("Французский фильм", 2020), 10),
    Movie(MovieName("Французский фильм", 2020), 1),
    Movie(MovieName("Немецкий фильм", 2020), 10),
    Movie(MovieName("Немецкий фильм", 2020), 1),
    Movie(MovieName("Немецкий фильм", 2020), 10),
    Movie(MovieName("Испанский фильм", 2020), 1),
    Movie(MovieName("Испанский фильм", 2020), 2)
)


fun getRussianMovies() = listOf(
    Movie(MovieName("Русский фильм", 2020), 10),
    Movie(MovieName("Русский фильм", 2020), 1)
)
