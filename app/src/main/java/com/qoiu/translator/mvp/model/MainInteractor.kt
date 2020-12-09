package com.qoiu.translator.mvp.model

import com.qoiu.translator.di.NAME_LOCAL
import com.qoiu.translator.di.NAME_REMOTE
import com.qoiu.translator.mvp.model.data.AppState
import com.qoiu.translator.mvp.model.data.SearchResults
import com.qoiu.translator.mvp.presenter.Interactor
import com.qoiu.translator.mvp.presenter.Repository
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Named

class MainInteractor @Inject constructor (
    @Named(NAME_REMOTE) val remoteRepository: Repository<List<SearchResults>>,
    @Named(NAME_LOCAL) val localRepository: Repository<List<SearchResults>>
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