package com.qoiu.translator.data

import com.google.gson.annotations.SerializedName

class Meanings (
    @field:SerializedName("translation") val translation: Translation?,
    @field:SerializedName("transcription") val transcription: String?,
    @field:SerializedName("imageUrl") val imageUrl: String?
)