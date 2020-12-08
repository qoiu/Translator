package com.qoiu.translator.mvvm.viewmodel

import androidx.lifecycle.LiveData
import com.qoiu.translator.mvp.RepositoryImplementation
import com.qoiu.translator.mvp.model.MainInteractor
import com.qoiu.translator.mvp.model.data.AppState
import com.qoiu.translator.mvp.model.data.DataSourceRemote
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    private val interactor:MainInteractor = MainInteractor(
RepositoryImplementation(DataSourceRemote()),
//Add local source
RepositoryImplementation(DataSourceRemote())
)
) : BaseViewModel<AppState>() {

    override fun getData(word: String, isOnline: Boolean): LiveData<AppState> {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe{liveDataForViewToObserve.value = AppState.Loading(null)}
                .subscribeWith(getObserver())
        )
        return super.getData(word, isOnline)
    }

    private fun getObserver() : DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>(){
            override fun onNext(state: AppState) {
                liveDataForViewToObserve.value = state
            }

            override fun onError(e: Throwable) {
                liveDataForViewToObserve.value= AppState.Error(e)
            }

            override fun onComplete() {
            }
        }
    }
}