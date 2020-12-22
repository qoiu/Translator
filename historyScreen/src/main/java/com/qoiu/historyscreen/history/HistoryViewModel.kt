package com.qoiu.historyscreen.history

import androidx.lifecycle.LiveData
import com.qoiu.historyscreen.parseLocalSearchResults
import com.qoiu.model.AppState
import kotlinx.coroutines.launch

class HistoryViewModel (private val interactor: HistoryInteractor) :
    com.qoiu.core.BaseViewModel<AppState>() {

    private val liveDataForViewToObserve: LiveData<AppState> = mutableLiveData

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    override fun getData(word: String, isOnline: Boolean) {
        mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(word, isOnline) }
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) {
        mutableLiveData.postValue(parseLocalSearchResults(interactor.getData(word, isOnline)))
    }

    override fun handleError(error: Throwable) {
        mutableLiveData.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        mutableLiveData.value = AppState.Success(null)
        super.onCleared()
    }
}