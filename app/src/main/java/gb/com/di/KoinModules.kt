package gb.com.di

import gb.com.model.data.WordDefinition
import gb.com.model.datasource.api.RetrofitImplementation
import gb.com.model.datasource.db.RoomDataBaseImplementation
import gb.com.model.repository.Repository
import gb.com.model.repository.RepositoryImplementation
import gb.com.presenter.MainInteractor
import gb.com.view.main.MainViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal const val NAME_REMOTE = "Remote"
internal const val NAME_LOCAL = "Local"

val application = module {
    single<Repository<List<WordDefinition>>>(named(NAME_REMOTE)) {
        RepositoryImplementation(RetrofitImplementation())
    }
    single<Repository<List<WordDefinition>>>(named(NAME_LOCAL)){
        RepositoryImplementation(RoomDataBaseImplementation())
    }
}

val mainScreen = module {
    factory { MainInteractor(get(named(NAME_REMOTE))) }
    factory { MainViewModel(get()) }
}