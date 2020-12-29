package com.qoiu.model

import com.google.gson.annotations.SerializedName

class TranslationDto(
    @field:SerializedName("text") val translation: String?
)
