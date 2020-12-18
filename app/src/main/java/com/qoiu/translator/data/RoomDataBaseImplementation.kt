package com.qoiu.translator.data

import com.qoiu.translator.DataSourceLocal
import com.qoiu.translator.room.HistoryDao
import com.qoiu.translator.ui.convertDataModelSuccessToEntity
import com.qoiu.translator.ui.mapHistoryEntityToSearchResult

class RoomDataBaseImplementation(private val historyDao: HistoryDao) :
    DataSourceLocal<List<SearchResults>> {

    override suspend fun getData(word: String): List<SearchResults> {
        return mapHistoryEntityToSearchResult(historyDao.all())
    }

    override suspend fun saveToDB(appState: AppState) {
        convertDataModelSuccessToEntity(appState)?.let {
            historyDao.insert(it)
        }
    }
}
