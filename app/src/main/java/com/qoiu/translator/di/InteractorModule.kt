package com.qoiu.translator.di

import com.qoiu.translator.mvp.model.MainInteractor
import com.qoiu.translator.mvp.model.data.SearchResults
import com.qoiu.translator.mvp.presenter.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class InteractorModule {

    @Provides
    internal fun provideInteractor(
        @Named(NAME_REMOTE) repositoryRemote: Repository<List<SearchResults>>,
        @Named(NAME_LOCAL) repositoryLocal: Repository<List<SearchResults>>
    ) = MainInteractor(repositoryRemote,repositoryLocal)
}