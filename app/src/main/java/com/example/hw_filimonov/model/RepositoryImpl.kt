package com.example.hw_filimonov.model

class RepositoryImpl : Repository {

    override fun getMovieFromServer(): Movie {
        return Movie()
    }

    override fun getMovieFromLocalStorageRus(): Movie {
        return Movie()
    }

    override fun getMovieFromLocalStorageWorld(): Movie {
        return Movie()
    }
}