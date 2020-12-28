package com.qoiu.historyscreen

import com.qoiu.model.AppState
import com.qoiu.model.Meanings
import com.qoiu.model.SearchResults

fun parseLocalSearchResults(data: AppState): AppState {
    return AppState.Success(mapResult(data, false))
}

private fun mapResult(
    data: AppState,
    isOnline: Boolean
): List<SearchResults> {
    val newSearchResults = arrayListOf<SearchResults>()
    when (data) {
        is AppState.Success -> {
            getSuccessResultData(
                data,
                isOnline,
                newSearchResults
            )
        }
    }
    return newSearchResults
}

private fun getSuccessResultData(
    data: AppState.Success,
    isOnline: Boolean,
    newDataModels: ArrayList<SearchResults>
) {
    val dataModels: List<SearchResults> = data.data as List<SearchResults>
    if (dataModels.isNotEmpty()) {
        if (isOnline) {
            for (searchResult in dataModels) {
                parseOnlineResult(
                    searchResult,
                    newDataModels
                )
            }
        } else {
            for (searchResult in dataModels) {
                newDataModels.add(
                    SearchResults(
                        searchResult.text,
                        arrayListOf()
                    )
                )
            }
        }
    }
}

private fun parseOnlineResult(dataModel: SearchResults, newDataModels: ArrayList<SearchResults>) {
    if (!dataModel.text.isNullOrBlank() && !dataModel.meanings.isNullOrEmpty()) {
        val newMeanings = arrayListOf<Meanings>()
        for (meaning in dataModel.meanings!!) {
            if (meaning.translation != null && !meaning.translation!!.translation.isNullOrBlank()) {
                newMeanings.add(
                    Meanings(
                        meaning.translation,
                        meaning.transcription,
                        meaning.imageUrl
                    )
                )
            }
        }
        if (newMeanings.isNotEmpty()) {
            newDataModels.add(
                SearchResults(
                    dataModel.text,
                    newMeanings
                )
            )
        }
    }
}

