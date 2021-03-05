package com.example.hw_filimonov.model

data class Movie(
    val movieName: MovieName = getDefaultMovieName(),
    val rating: Int = 0
)

fun getDefaultMovieName() = MovieName("Movie1", 2021)
