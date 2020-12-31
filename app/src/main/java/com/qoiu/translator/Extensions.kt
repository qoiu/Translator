package com.qoiu.translator

import com.qoiu.model.AppState
import com.qoiu.model.Meanings
import com.qoiu.model.SearchResults


fun convertMeaningsToString(meanings: List<Meanings>): String {
    var meaningsSeparatedByComma = String()
    for ((index, meaning) in meanings.withIndex()) {
        meaningsSeparatedByComma += if (index + 1 != meanings.size) {
            String.format("%s%s", meaning.translatedMeaning.translatedMeaning, ", ")
        } else {
            meaning.translatedMeaning.translatedMeaning
        }
    }
    return meaningsSeparatedByComma
}

fun parseSearchResults(data: AppState): AppState {
    val newSearchResults = arrayListOf<SearchResults>()
    when (data) {
        is AppState.Success -> {
            val searchResults = data.data
            if (!searchResults.isNullOrEmpty()) {
                for (searchResult in searchResults) {
                    parseResult(
                        searchResult,
                        newSearchResults
                    )
                }
            }
        }
    }

    return AppState.Success(newSearchResults)
}


private fun parseResult(dataModel: SearchResults, newDataModels: ArrayList<SearchResults>) {
    if (!dataModel.text.isBlank() && !dataModel.meanings.isNullOrEmpty()) {
        val newMeanings = arrayListOf<Meanings>()
        for (meaning in dataModel.meanings) {
                newMeanings.add(
                    Meanings(
                        meaning.translatedMeaning,
                        meaning.transcription,
                        meaning.imageUrl
                    )
                )
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
