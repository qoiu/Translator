package com.qoiu.translator.mvp.model.data

import com.qoiu.translator.DataSource

class DataSourceRemote(private val remoteProvider: RetrofitImplementation = RetrofitImplementation()) :
    DataSource<List<SearchResults>> {
    override suspend fun getData(word: String): List<SearchResults> =
        remoteProvider.getData(word)
}
