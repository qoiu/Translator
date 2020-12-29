package com.qoiu.model

sealed class AppState {
    data class Success(val data: List<SearchResults>?) : AppState()
    data class Error(val error: Throwable) : AppState()
    data class Loading(val progress: Int?) : AppState()
}
