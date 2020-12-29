package com.qoiu.repository.data

import com.qoiu.model.AppState
import com.qoiu.model.SearchResultsDto

class RepositoryImplementationLocal(private val dataSource: DataSourceLocal<List<SearchResultsDto>>) :
    RepositoryLocal<List<SearchResultsDto>> {

    override suspend fun getData(word: String): List<SearchResultsDto> {
        return dataSource.getData(word)
    }

    override suspend fun saveToDB(appState: AppState) {
        dataSource.saveToDB(appState)
    }
}
