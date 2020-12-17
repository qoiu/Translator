package com.qoiu.translator.mvvm.viewmodel

import androidx.lifecycle.LiveData
import com.qoiu.translator.mvp.RepositoryImplementation
import com.qoiu.translator.mvp.model.MainInteractor
import com.qoiu.translator.mvp.model.data.AppState
import com.qoiu.translator.mvp.model.data.DataSourceRemote
import com.qoiu.translator.parseSearchResults
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val interactor:MainInteractor
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