package com.qoiu.repository

import com.qoiu.model.*
import com.qoiu.repository.room.HistoryEntity

fun mapHistoryEntityToSearchResult(list: List<HistoryEntity>): List<SearchResultsDto> {
    val searchResult = ArrayList<SearchResultsDto>()
    if (!list.isNullOrEmpty()) {
        for (entity in list) {
            searchResult.add(SearchResultsDto(entity.word, null))
        }
    }
    return searchResult
}


fun mapSearchResultToResult(searchResults: List<SearchResultsDto>): List<SearchResults> {
    return searchResults.map { searchResult ->
        var meanings: List<Meanings> = listOf()
        searchResult.meanings?.let {
            meanings = it.map { meaningsDto ->
                Meanings(
                    TranslatedMeaning(meaningsDto?.translationDto?.translation ?: ""),
                    meaningsDto?.transcription ?:"",
                    meaningsDto?.imageUrl ?: ""
                )
            }
        }
        SearchResults(searchResult.text ?: "", meanings)
    }
}

fun convertDataModelSuccessToEntity(appState: AppState): HistoryEntity? {
    return when (appState) {
        is AppState.Success -> {
            val searchResult = appState.data
            if (searchResult.isNullOrEmpty() || searchResult[0].text.isEmpty()) {
                null
            } else {
                HistoryEntity(
                    searchResult[0].text,
                    " ",
                    null
                )
            }
        }
        else -> null
    }
}