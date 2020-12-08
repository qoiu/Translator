package com.qoiu.translator.mvp.model.data

import com.qoiu.translator.mvp.presenter.DataSource
import io.reactivex.Observable

class DataSourceRemote(private val remoteProvider: RetrofitImplementation = RetrofitImplementation()) :
    DataSource<List<SearchResults>> {
    override fun getData(word: String): Observable<List<SearchResults>> =
        remoteProvider.getData(word)
}