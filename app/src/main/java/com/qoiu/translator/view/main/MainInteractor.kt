package com.qoiu.translator.view.main

import com.qoiu.core.Interactor
import com.qoiu.model.AppState
import com.qoiu.model.SearchResultsDto
import com.qoiu.repository.data.Repository
import com.qoiu.repository.data.RepositoryLocal
import com.qoiu.repository.mapSearchResultToResult

class MainInteractor (
    val remoteRepository: Repository<List<SearchResultsDto>>,
    val localRepository: RepositoryLocal<List<SearchResultsDto>>
    ) : Interactor<AppState> {

        override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
            val appState: AppState
            if(fromRemoteSource){
                val searchResults=remoteRepository.getData(word)
                appState = AppState.Success(mapSearchResultToResult(searchResults))
                localRepository.saveToDB(appState)
            }else{
                appState= AppState.Success(mapSearchResultToResult(localRepository.getData(word)))
            }
            return appState
        }
}