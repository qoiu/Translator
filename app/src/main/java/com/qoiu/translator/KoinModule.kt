package com.qoiu.translator

import androidx.room.Room
import com.qoiu.model.SearchResultsDto
import com.qoiu.repository.data.*
import com.qoiu.repository.room.HistoryDataBase
import com.qoiu.translator.view.main.MainActivity
import com.qoiu.translator.view.main.MainInteractor
import com.qoiu.translator.view.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module
fun injectDependencies() = loadModules

private val loadModules by lazy {
    loadKoinModules(listOf(application, mainScreen))
}

val application = module {

    single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build() }
    single { get<HistoryDataBase>().historyDao() }
    single<Repository<List<SearchResultsDto>>> {
        RepositoryImplementation(
            RetrofitImplementation()
        )
    }
    single<RepositoryLocal<List<SearchResultsDto>>> {
        RepositoryImplementationLocal(
            RoomDataBaseImplementation(get())
        )
    }
}

val mainScreen = module {
    scope(named<MainActivity>()){
        scoped {MainInteractor(get(), get())}
        viewModel {MainViewModel(get())}
    }
}
