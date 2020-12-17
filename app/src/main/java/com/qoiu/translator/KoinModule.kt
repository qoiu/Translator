package com.qoiu.translator

import androidx.room.Room
import com.qoiu.translator.view.history.HistoryInteractor
import com.qoiu.translator.view.history.HistoryViewModel
import com.qoiu.translator.mvp.RepositoryImplementation
import com.qoiu.translator.view.main.MainInteractor
import com.qoiu.translator.mvp.model.data.RepositoryImplementationLocal
import com.qoiu.translator.mvp.model.data.RetrofitImplementation
import com.qoiu.translator.mvp.model.data.RoomDataBaseImplementation
import com.qoiu.translator.mvp.model.data.SearchResults
import com.qoiu.translator.view.main.MainViewModel
import com.qoiu.translator.room.HistoryDataBase
import org.koin.dsl.module

val application = module {

    single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build() }
    single { get<HistoryDataBase>().historyDao() }
    single<Repository<List<SearchResults>>> { RepositoryImplementation(RetrofitImplementation()) }
    single<RepositoryLocal<List<SearchResults>>> { RepositoryImplementationLocal(RoomDataBaseImplementation(get()))}
}

val mainScreen = module {
    factory {
        MainInteractor(
            get(),
            get()
        )
    }
    factory {
        MainViewModel(get())
    }
}

val historyScreen = module {
    factory { HistoryViewModel(get()) }
    factory { HistoryInteractor(get(),get()) }
}