package com.qoiu.translator.mvp.presenter

import com.qoiu.translator.mvp.model.data.AppState
import com.qoiu.translator.mvp.model.data.SearchResults
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface View{
    fun renderData(appState : AppState)
}

interface Presenter<T : AppState, V : View>{
    fun attachView(view: V)
    fun detachView(view: V)
    fun getData(word: String, isOnline: Boolean)
}

interface Interactor<T>{
    fun getData(word: String, fromRemoteSource: Boolean): Observable<T>
}

interface Repository<T> {
    fun getData(word: String): Observable<T>
}

interface DataSource<T> {
    fun getData(word: String): Observable<T>
}

interface ApiService {

    @GET("words/search")
    fun search(@Query("search") wordToSearch: String): Observable<List<SearchResults>>
}