package com.qoiu.translator

import com.qoiu.translator.mvp.model.data.AppState
import com.qoiu.translator.mvp.model.data.SearchResults
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query


interface Interactor<T>{
    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}

interface Repository<T> {
    suspend fun getData(word: String): T
}

interface DataSourceRemote<T> {
    suspend fun getData(word: String):T
}

interface ApiService {

    @GET("words/search")
    fun search(@Query("search") wordToSearch: String): Deferred<List<SearchResults>>
}


interface DataSource<T> {

    suspend fun getData(word: String): T
}

interface DataSourceLocal<T> : DataSource<T> {

    suspend fun saveToDB(appState: AppState)
}

interface RepositoryLocal<T> : Repository<T> {

    suspend fun saveToDB(appState: AppState)
}