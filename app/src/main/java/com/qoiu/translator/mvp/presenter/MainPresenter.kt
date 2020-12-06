package com.qoiu.translator.mvp.presenter

import com.qoiu.translator.mvp.RepositoryImplementation
import com.qoiu.translator.mvp.model.data.DataModel
import com.qoiu.translator.mvp.model.MainInteractor
import com.qoiu.translator.mvp.model.data.DataSourceRemote
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MainPresenter<T : DataModel, V: View>(
    private val interactor: MainInteractor = MainInteractor(
        RepositoryImplementation(DataSourceRemote()),
        //Add local source
        RepositoryImplementation(DataSourceRemote())
    ),
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()
) : Presenter<T, V> {

    private var currentView : V? =null

    override fun attachView(view: V) {
        if(view != currentView) currentView = view
    }

    override fun detachView(view: V) {
        compositeDisposable.clear()
        if(view == currentView) currentView = null
    }

    override fun getData(word: String, isOnline: Boolean) {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(doOnSubscribe())
                .subscribeWith(getObserver())
        )
    }

    private fun doOnSubscribe() : (Disposable) -> Unit = {
        currentView?.renderData(DataModel.Loading(null))
    }

    private fun getObserver() : DisposableObserver<DataModel>{
        return object : DisposableObserver<DataModel>(){
            override fun onNext(t: DataModel) {
                currentView?.renderData(t)
            }

            override fun onError(e: Throwable) {
                currentView?.renderData(DataModel.Error(e))
            }

            override fun onComplete() {
            }
        }
    }
}