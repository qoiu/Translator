package com.qoiu.repository.data

import com.qoiu.model.SearchResultsDto
import com.qoiu.repository.convertDataModelSuccessToEntity
import com.qoiu.repository.mapHistoryEntityToSearchResult
import com.qoiu.repository.room.HistoryDao

class RoomDataBaseImplementation(private val historyDao: HistoryDao) :
    DataSourceLocal<List<SearchResultsDto>> {

    override suspend fun getData(word: String): List<SearchResultsDto> {
        return mapHistoryEntityToSearchResult(historyDao.all())
    }

    override suspend fun saveToDB(appState: com.qoiu.model.AppState) {
        convertDataModelSuccessToEntity(appState)?.let {
            historyDao.insert(it)
        }
    }
}
