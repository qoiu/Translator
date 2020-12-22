package com.qoiu.translator

import androidx.room.Room
import com.qoiu.historyscreen.history.HistoryInteractor
import com.qoiu.historyscreen.history.HistoryViewModel
import com.qoiu.model.SearchResults
import com.qoiu.repository.data.*
import com.qoiu.repository.room.HistoryDataBase
import com.qoiu.translator.view.main.MainInteractor
import com.qoiu.translator.view.main.MainViewModel
import org.koin.dsl.module

val application = module {

    single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build() }
    single { get<HistoryDataBase>().historyDao() }
    single<Repository<List<SearchResults>>> {
        RepositoryImplementation(
            RetrofitImplementation()
        )
    }
    single<RepositoryLocal<List<SearchResults>>> {
        RepositoryImplementationLocal(
            RoomDataBaseImplementation(get())
        )
    }
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
    factory { HistoryInteractor(get(), get()) }
}