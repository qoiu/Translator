package com.qoiu.repository.data



interface DataSource<T> {

    suspend fun getData(word: String): T
}

