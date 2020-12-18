package com.qoiu.translator.data

import com.qoiu.translator.data.SearchResults
import com.qoiu.translator.DataSource
import com.qoiu.translator.Repository

class RepositoryImplementation(private val dataSource: DataSource<List<SearchResults>>) :
    Repository<List<SearchResults>> {

    override suspend fun getData(word: String): List<SearchResults> {
        return dataSource.getData(word)
    }

}