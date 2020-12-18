package com.qoiu.translator.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.qoiu.translator.data.AppState
import kotlinx.coroutines.*

abstract class BaseViewModel<T : AppState>(
   protected open val mutableLiveData: MutableLiveData<T> = MutableLiveData()
) : ViewModel() {

    protected val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
    + SupervisorJob()
    + CoroutineExceptionHandler{_,throwable->
            handleError(throwable)
        }
    )

    abstract fun getData(word: String, isOnline: Boolean)

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    abstract fun handleError(error: Throwable)

    protected fun cancelJob(){
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }
}