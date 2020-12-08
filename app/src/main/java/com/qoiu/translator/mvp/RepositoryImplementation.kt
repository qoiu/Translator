package com.qoiu.translator.mvp

import com.qoiu.translator.mvp.model.data.SearchResults
import com.qoiu.translator.mvp.presenter.DataSource
import com.qoiu.translator.mvp.presenter.Repository
import io.reactivex.Observable

class RepositoryImplementation(private val dataSource: DataSource<List<SearchResults>>) : Repository<List<SearchResults>>{
    override fun getData(word: String): Observable<List<SearchResults>> {
        return dataSource.getData(word)
    }
}