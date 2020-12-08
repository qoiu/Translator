package com.qoiu.translator.mvp.model

import com.qoiu.translator.mvp.model.data.AppState
import com.qoiu.translator.mvp.model.data.SearchResults
import com.qoiu.translator.mvp.presenter.Interactor
import com.qoiu.translator.mvp.presenter.Repository
import io.reactivex.Observable

class MainInteractor (
    private val remoteRepository: Repository<List<SearchResults>>,
    private val localRepository: Repository<List<SearchResults>>
    ) : Interactor<AppState> {
        // Интерактор лишь запрашивает у репозитория данные, детали имплементации
        // интерактору неизвестны
        override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
            return if (fromRemoteSource) {
                remoteRepository.getData(word).map { AppState.Success(it) }
            } else {
                localRepository.getData(word).map { AppState.Success(it) }
            }
        }
}