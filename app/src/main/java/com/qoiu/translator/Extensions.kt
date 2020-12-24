package com.qoiu.translator

import com.qoiu.model.AppState
import com.qoiu.model.Meanings
import com.qoiu.model.SearchResults


fun convertMeaningsToString(meanings: List<Meanings>): String {
    var meaningsSeparatedByComma = String()
    for ((index, meaning) in meanings.withIndex()) {
        meaningsSeparatedByComma += if (index + 1 != meanings.size) {
            String.format("%s%s", meaning.translation?.translation, ", ")
        } else {
            meaning.translation?.translation
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
