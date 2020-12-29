package com.qoiu.repository.data

import com.qoiu.model.SearchResultsDto

class RepositoryImplementation(private val dataSource: DataSource<List<SearchResultsDto>>) :
    Repository<List<SearchResultsDto>> {

    override suspend fun getData(word: String): List<SearchResultsDto> {
        return dataSource.getData(word)
    }

}