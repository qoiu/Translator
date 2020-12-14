package com.qoiu.translator.mvp

import com.qoiu.translator.mvp.model.data.SearchResults
import com.qoiu.translator.DataSource
import com.qoiu.translator.Repository

class RepositoryImplementation(private val dataSource: DataSource<List<SearchResults>>) :
    Repository<List<SearchResults>> {

    override suspend fun getData(word: String): List<SearchResults> {
        return dataSource.getData(word)
    }

}