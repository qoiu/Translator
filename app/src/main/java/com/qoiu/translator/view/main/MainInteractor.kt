package com.qoiu.translator.view.main

import com.qoiu.translator.data.AppState
import com.qoiu.translator.data.SearchResults
import com.qoiu.translator.Interactor
import com.qoiu.translator.Repository
import com.qoiu.translator.RepositoryLocal

class MainInteractor (
    val remoteRepository: Repository<List<SearchResults>>,
    val localRepository: RepositoryLocal<List<SearchResults>>
    ) : Interactor<AppState> {

        override suspend fun getData(word: String, fromRemoteSource: Boolean):AppState {
            val appState: AppState
            if(fromRemoteSource){
                val searchResults=remoteRepository.getData(word)
                appState = AppState.Success(searchResults)
                localRepository.saveToDB(appState)
            }else{
                appState=AppState.Success(localRepository.getData(word))
            }
            return appState
        }
}