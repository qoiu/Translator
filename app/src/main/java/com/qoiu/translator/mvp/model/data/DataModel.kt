package com.qoiu.translator.mvp.model.data

sealed class DataModel {
    data class Success(val data: List<SearchResults>) : DataModel()
    data class Error(val error: Throwable) : DataModel()
    data class Loading(val progress: Int?) : DataModel()
}