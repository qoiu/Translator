package com.qoiu.translator.mvp.model

import com.qoiu.translator.mvp.model.data.DataModel
import com.qoiu.translator.mvp.model.data.SearchResults
import com.qoiu.translator.mvp.presenter.Interactor
import com.qoiu.translator.mvp.presenter.Repository
import io.reactivex.Observable

class MainInteractor (
    private val remoteRepository: Repository<List<SearchResults>>,
    private val localRepository: Repository<List<SearchResults>>
    ) : Interactor<DataModel> {
        // Интерактор лишь запрашивает у репозитория данные, детали имплементации
        // интерактору неизвестны
        override fun getData(word: String, fromRemoteSource: Boolean): Observable<DataModel> {
            return if (fromRemoteSource) {
                remoteRepository.getData(word).map { DataModel.Success(it) }
            } else {
                localRepository.getData(word).map { DataModel.Success(it) }
            }
        }
}