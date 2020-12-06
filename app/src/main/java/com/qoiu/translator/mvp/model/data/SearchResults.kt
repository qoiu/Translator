package com.qoiu.translator.mvp.model.data

import com.google.gson.annotations.SerializedName

class SearchResults(
        @field:SerializedName("text") val text: String?,
        @field:SerializedName("meanings") val meanings: List<Meanings>?
)