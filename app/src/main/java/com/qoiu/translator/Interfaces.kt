package com.qoiu.translator

import com.qoiu.translator.mvp.model.data.AppState
import com.qoiu.translator.mvp.model.data.SearchResults
import kotlinx.coroutines.Deferred
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
    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}

interface Repository<T> {
    suspend fun getData(word: String): T
}

interface DataSource<T> {
    suspend fun getData(word: String):T
}

interface ApiService {

    @GET("words/search")
    fun search(@Query("search") wordToSearch: String): Deferred<List<SearchResults>>
}