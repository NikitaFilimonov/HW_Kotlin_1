package com.example.hw_filimonov.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hw_filimonov.model.Repository
import com.example.hw_filimonov.model.RepositoryImpl
import java.lang.Thread.sleep

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl()
) :
    ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getMovieFromLocalSourceRus() = getDataFromLocalSource(isRussian = true)

    fun getMovieFromLocalSourceWorld() = getDataFromLocalSource(isRussian = false)

    fun getMovieFromRemoteSource() = getDataFromLocalSource(isRussian = true)

    private fun getDataFromLocalSource(isRussian: Boolean) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(1000)
            liveDataToObserve.postValue(AppState.Success(if (isRussian) repositoryImpl.getMovieFromLocalStorageRus() else repositoryImpl.getMovieFromLocalStorageWorld()))
        }.start()
    }
}
