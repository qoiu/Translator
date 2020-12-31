package com.qoiu.model

import com.google.gson.annotations.SerializedName

class SearchResultsDto(
        @field:SerializedName("text") val text: String?,
        @field:SerializedName("meanings") val meanings: List<MeaningsDto?>?
)
