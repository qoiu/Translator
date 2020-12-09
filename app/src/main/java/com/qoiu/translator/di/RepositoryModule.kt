package com.qoiu.translator.di

import com.qoiu.translator.mvp.RepositoryImplementation
import com.qoiu.translator.mvp.model.data.RetrofitImplementation
import com.qoiu.translator.mvp.model.data.SearchResults
import com.qoiu.translator.mvp.presenter.DataSource
import com.qoiu.translator.mvp.presenter.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideRepositoryRemote(@Named(NAME_REMOTE) dataSourceRemote: DataSource<List<SearchResults>>)
            : Repository<List<SearchResults>> = RepositoryImplementation(dataSourceRemote)

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideRepositoryLocal(@Named(NAME_LOCAL) dataSourceRemote: DataSource<List<SearchResults>>)
            : Repository<List<SearchResults>> = RepositoryImplementation(dataSourceRemote)

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideDataSourceRemmote() : DataSource<List<SearchResults>> =
        RetrofitImplementation()

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideDataSourceLocale() : DataSource<List<SearchResults>> =
        RetrofitImplementation()
}