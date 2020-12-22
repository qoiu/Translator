package com.qoiu.repository.data

import com.qoiu.model.SearchResults

class DataSourceRemote(private val remoteProvider: RetrofitImplementation = RetrofitImplementation()) :
    DataSource<List<SearchResults>> {
    override suspend fun getData(word: String): List<SearchResults> =
        remoteProvider.getData(word)
}
