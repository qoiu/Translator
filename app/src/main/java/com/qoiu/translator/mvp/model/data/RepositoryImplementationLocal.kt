package com.qoiu.translator.mvp.model.data

import com.qoiu.translator.DataSourceLocal
import com.qoiu.translator.RepositoryLocal

class RepositoryImplementationLocal(private val dataSource: DataSourceLocal<List<SearchResults>>) :
    RepositoryLocal<List<SearchResults>> {

    override suspend fun getData(word: String): List<SearchResults> {
        return dataSource.getData(word)
    }

    override suspend fun saveToDB(appState: AppState) {
        dataSource.saveToDB(appState)
    }
}
