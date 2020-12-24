package com.qoiu.repository.data

interface Repository<T> {
    suspend fun getData(word: String): T
}