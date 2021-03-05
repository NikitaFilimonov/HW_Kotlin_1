package com.example.hw_filimonov.viewmodel

import com.example.hw_filimonov.model.Movie

sealed class AppState {
    data class Success(val movieData: Movie) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
