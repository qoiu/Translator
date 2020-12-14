package com.qoiu.translator

import com.qoiu.translator.di.NAME_LOCAL
import com.qoiu.translator.di.NAME_REMOTE
import com.qoiu.translator.mvp.RepositoryImplementation
import com.qoiu.translator.mvp.model.MainInteractor
import com.qoiu.translator.mvp.model.data.RetrofitImplementation
import com.qoiu.translator.mvp.model.data.SearchResults
import com.qoiu.translator.mvvm.viewmodel.MainViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {
    single<Repository<List<SearchResults>>>(named(NAME_REMOTE)){
        RepositoryImplementation(RetrofitImplementation())
    }
    single<Repository<List<SearchResults>>>(named(NAME_LOCAL)){
        RepositoryImplementation(
            //Local implementation
            RetrofitImplementation()
        )
    }
}

val mainScreen = module {
    factory {
        MainInteractor(
            get(named(NAME_REMOTE)),
            get(named(NAME_LOCAL))
        )
    }
    factory {
        MainViewModel(get())
    }
}