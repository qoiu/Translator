package com.qoiu.historyscreen.history

import com.qoiu.core.Interactor
import com.qoiu.model.AppState
import com.qoiu.model.SearchResultsDto
import com.qoiu.repository.data.Repository
import com.qoiu.repository.data.RepositoryLocal
import com.qoiu.repository.mapSearchResultToResult

class HistoryInteractor(
    private val repositoryRemote: Repository<List<SearchResultsDto>>,
    private val repositoryLocal: RepositoryLocal<List<SearchResultsDto>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(
            mapSearchResultToResult(
                if (fromRemoteSource) {
                    repositoryRemote
                } else {
                    repositoryLocal
                }.getData(word)
            )
        )
    }

}