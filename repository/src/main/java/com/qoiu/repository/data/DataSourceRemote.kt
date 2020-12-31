package com.qoiu.repository.data

import com.qoiu.model.SearchResultsDto

class DataSourceRemote(private val remoteProvider: RetrofitImplementation = RetrofitImplementation()) :
    DataSource<List<SearchResultsDto>> {
    override suspend fun getData(word: String): List<SearchResultsDto> =
        remoteProvider.getData(word)
}
