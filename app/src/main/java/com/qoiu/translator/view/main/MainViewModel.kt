package com.qoiu.translator.view.main

import androidx.lifecycle.LiveData
import com.qoiu.translator.data.AppState
import com.qoiu.translator.view.BaseViewModel
import com.qoiu.translator.parseSearchResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val interactor: MainInteractor
) : BaseViewModel<AppState>() {

    private val liveDataForViewToObserve: LiveData<AppState> = mutableLiveData

    fun subscribe() : LiveData<AppState>{
        return liveDataForViewToObserve
    }

    override fun getData(word: String, isOnline: Boolean) {
       mutableLiveData.value=AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch {
            startInteractor(word,isOnline)
        }
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean){
        withContext(Dispatchers.IO){
            val result = parseSearchResults(interactor.getData(word,isOnline))
            withContext(Dispatchers.Main){
                mutableLiveData.value = result
            }
        }
    }

    override fun handleError(error: Throwable) {
        mutableLiveData.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        mutableLiveData.value=AppState.Success(null)
        super.onCleared()
    }
}