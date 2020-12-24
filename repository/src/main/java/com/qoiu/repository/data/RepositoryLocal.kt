package com.qoiu.repository.data

import com.qoiu.model.AppState

interface RepositoryLocal<T> : Repository<T> {
    suspend fun saveToDB(appState: AppState)
}