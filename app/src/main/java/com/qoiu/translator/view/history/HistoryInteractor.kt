package com.qoiu.translator.view.history

import com.qoiu.translator.Interactor
import com.qoiu.translator.Repository
import com.qoiu.translator.RepositoryLocal
import com.qoiu.translator.data.AppState
import com.qoiu.translator.data.SearchResults

class HistoryInteractor (
    private val repositoryRemote: Repository<List<SearchResults>>,
    private val repositoryLocal: RepositoryLocal<List<SearchResults>>
    ) : Interactor<AppState>{

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