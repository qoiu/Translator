package com.qoiu.historyscreen.history

import com.qoiu.core.Interactor
import com.qoiu.model.AppState
import com.qoiu.model.SearchResults
import com.qoiu.repository.data.Repository
import com.qoiu.repository.data.RepositoryLocal

class HistoryInteractor (
    private val repositoryRemote: Repository<List<SearchResults>>,
    private val repositoryLocal: RepositoryLocal<List<SearchResults>>
    ) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(
            if (fromRemoteSource) {
                repositoryRemote
            } else {
                repositoryLocal
            }.getData(word)
        )
    }
}