package com.qoiu.model

import com.google.gson.annotations.SerializedName

class MeaningsDto (
    @field:SerializedName("translation") val translationDto: TranslationDto?,
    @field:SerializedName("transcription") val transcription: String?,
    @field:SerializedName("imageUrl") val imageUrl: String?
)