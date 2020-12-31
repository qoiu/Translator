package com.qoiu.model

data class SearchResults(
    val text: String = "",
    val meanings: List<Meanings> = listOf()
)

data class Meanings(
    val translatedMeaning: TranslatedMeaning = TranslatedMeaning(),
    val transcription: String = "",
    val imageUrl: String = ""
)

data class TranslatedMeaning(val translatedMeaning: String = "")