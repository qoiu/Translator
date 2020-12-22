package com.qoiu.translator.view.main

import com.qoiu.core.Interactor
import com.qoiu.model.AppState
import com.qoiu.model.SearchResults
import com.qoiu.repository.data.Repository
import com.qoiu.repository.data.RepositoryLocal

class MainInteractor (
    val remoteRepository: Repository<List<SearchResults>>,
    val localRepository: RepositoryLocal<List<SearchResults>>
    ) : Interactor<AppState> {

        override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
            val appState: AppState
            if(fromRemoteSource){
                val searchResults=remoteRepository.getData(word)
                appState = AppState.Success(searchResults)
                localRepository.saveToDB(appState)
            }else{
                appState= AppState.Success(localRepository.getData(word))
            }
            return appState
        }
}