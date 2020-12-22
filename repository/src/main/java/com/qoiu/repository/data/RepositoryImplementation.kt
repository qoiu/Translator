package com.qoiu.repository.data

import com.qoiu.model.SearchResults

class RepositoryImplementation(private val dataSource: DataSource<List<SearchResults>>) :
    Repository<List<SearchResults>> {

    override suspend fun getData(word: String): List<SearchResults> {
        return dataSource.getData(word)
    }

}