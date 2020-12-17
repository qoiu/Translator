package com.qoiu.translator.view.main

import com.qoiu.translator.mvp.model.data.AppState
import com.qoiu.translator.mvp.model.data.SearchResults
import com.qoiu.translator.Interactor
import com.qoiu.translator.Repository

class MainInteractor (
    val remoteRepository: Repository<List<SearchResults>>,
    val localRepository: Repository<List<SearchResults>>
    ) : Interactor<AppState> {
        // Интерактор лишь запрашивает у репозитория данные, детали имплементации
        // интерактору неизвестны
        override suspend fun getData(word: String, fromRemoteSource: Boolean):AppState {
            return AppState.Success(remoteRepository.getData(word))
        }
}