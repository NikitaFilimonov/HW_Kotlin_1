package com.example.hw_filimonov.model

interface Repository {
    fun getMovieFromServer(): Movie
    fun getMovieFromLocalStorageRus(): Movie
    fun getMovieFromLocalStorageWorld(): Movie
}
