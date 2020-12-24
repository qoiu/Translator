package com.qoiu.repository.data

import com.qoiu.model.AppState
import com.qoiu.model.SearchResults

class RepositoryImplementationLocal(private val dataSource: DataSourceLocal<List<SearchResults>>) :
    RepositoryLocal<List<SearchResults>> {

    override suspend fun getData(word: String): List<SearchResults> {
        return dataSource.getData(word)
    }

    override suspend fun saveToDB(appState: AppState) {
        dataSource.saveToDB(appState)
    }
}
