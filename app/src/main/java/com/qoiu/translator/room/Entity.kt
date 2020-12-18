package com.qoiu.translator.room

import android.accounts.AuthenticatorDescription
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices =  arrayOf(Index(value = arrayOf("word"), unique = true)))
class HistoryEntity(
    @field:PrimaryKey
    @field:ColumnInfo(name = "word")
    var word: String,
    @field:ColumnInfo(name = "transcription")
    var transcription: String,
    @field:ColumnInfo(name = "description")
    var description: String?
)