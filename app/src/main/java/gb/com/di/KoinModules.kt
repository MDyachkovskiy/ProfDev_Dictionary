package gb.com.di

import androidx.room.Room
import gb.com.model.data.WordDefinition
import gb.com.model.database.HistoryDataBase
import gb.com.model.datasource.api.RetrofitImplementation
import gb.com.model.datasource.db.RoomDataBaseImplementation
import gb.com.model.repository.Repository
import gb.com.model.repository.RepositoryImplementation
import gb.com.model.repository.RepositoryImplementationLocal
import gb.com.model.repository.RepositoryLocal
import gb.com.presenter.HistoryInteractor
import gb.com.presenter.MainInteractor
import gb.com.view.history.HistoryViewModel
import gb.com.view.search.SearchViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal const val NAME_REMOTE = "Remote"
internal const val NAME_LOCAL = "Local"

val application = module {
    single { Room.databaseBuilder( get(), HistoryDataBase::class.java, "HistoryDB").build() }
    single { get<HistoryDataBase>().historyDao() }
    single <Repository<List<WordDefinition>>>(named(NAME_REMOTE)) {
        RepositoryImplementation(RetrofitImplementation())
    }
    single <RepositoryLocal<List<WordDefinition>>>(named(NAME_LOCAL)) {
        RepositoryImplementationLocal(RoomDataBaseImplementation(get()))
    }
}

val mainScreen = module {
    factory { MainInteractor( get(named(NAME_REMOTE)), get(named(NAME_LOCAL)) ) }
    factory { SearchViewModel( get() ) }
}

val historyScreen = module {
    factory { HistoryViewModel( get() ) }
    factory { HistoryInteractor( get(named(NAME_REMOTE)), get(named(NAME_LOCAL)) )}
}