package com.qoiu.repository

import com.qoiu.model.AppState
import com.qoiu.model.SearchResults
import com.qoiu.repository.room.HistoryEntity

fun mapHistoryEntityToSearchResult(list: List<HistoryEntity>): List<SearchResults> {
    val searchResult = ArrayList<SearchResults>()
    if (!list.isNullOrEmpty()) {
        for (entity in list) {
            searchResult.add(SearchResults(entity.word, null))
        }
    }
    return searchResult
}

fun convertDataModelSuccessToEntity(appState: AppState): HistoryEntity? {
    return when (appState) {
        is AppState.Success -> {
            val searchResult = appState.data
            if (searchResult.isNullOrEmpty() || searchResult[0].text.isNullOrEmpty()) {
                null
            } else {
                HistoryEntity(
                    searchResult[0].text!!,
                    " ",
                    null
                )
            }
        }
        else -> null
    }
}