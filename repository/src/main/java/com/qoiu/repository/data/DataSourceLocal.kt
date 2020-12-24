package com.qoiu.repository.data

interface DataSourceLocal<T> : DataSource<T> {

    suspend fun saveToDB(appState: com.qoiu.model.AppState)
}