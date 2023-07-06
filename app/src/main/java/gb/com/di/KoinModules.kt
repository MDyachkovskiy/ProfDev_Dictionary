package gb.com.di

import androidx.room.Room
import gb.com.model.data.wordDefinition.WordDefinition
import gb.com.model.database.HistoryDataBase
import gb.com.model.datasource.api.RetrofitImplementationForSkyengWord
import gb.com.model.datasource.api.RetrofitImplementationForWordDefinition
import gb.com.model.datasource.db.FavoriteDataBaseImplementation
import gb.com.model.datasource.db.RoomDataBaseImplementation
import gb.com.model.repository.Repository
import gb.com.model.repository.RepositoryFavorites
import gb.com.model.repository.RepositoryImplementation
import gb.com.model.repository.RepositoryImplementationFavorite
import gb.com.model.repository.RepositoryImplementationLocal
import gb.com.model.repository.RepositoryLocal
import gb.com.model.repository.RepositorySkyeng
import gb.com.presenter.FavoriteInteractor
import gb.com.presenter.HistoryInteractor
import gb.com.presenter.ImageInteractor
import gb.com.presenter.MainInteractor
import gb.com.view.favorite.FavoriteFragment
import gb.com.view.favorite.FavoriteViewModel
import gb.com.view.history.HistoryFragment
import gb.com.view.history.HistoryViewModel
import gb.com.view.image.ImageViewModel
import gb.com.view.search.SearchFragment
import gb.com.view.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal const val NAME_REMOTE = "Remote"
internal const val NAME_LOCAL = "Local"

val application = module {
    single { Room.databaseBuilder( get(), HistoryDataBase::class.java, "HistoryDB").build() }
    single { get<HistoryDataBase>().historyDao() }
    single { get<HistoryDataBase>().favoriteDao() }

    single <Repository<List<WordDefinition>>>(named(NAME_REMOTE)) {
        RepositoryImplementation(RetrofitImplementationForWordDefinition(),
        RetrofitImplementationForSkyengWord())
    }

    single <RepositoryLocal<List<WordDefinition>>>(named(NAME_LOCAL)) {
        RepositoryImplementationLocal(RoomDataBaseImplementation(get()))
    }

    single <RepositoryFavorites> {
        RepositoryImplementationFavorite(FavoriteDataBaseImplementation(get()))
    }

    single <RepositorySkyeng> {
        RepositoryImplementation(RetrofitImplementationForWordDefinition(),
            RetrofitImplementationForSkyengWord())
    }
}

val mainScreen = module {
    scope(named<SearchFragment>()) {
        scoped { MainInteractor( get(named(NAME_REMOTE)), get(named(NAME_LOCAL)), get() ) }
        viewModel { SearchViewModel( get() ) }
    }
}

val historyScreen = module {
    scope(named<HistoryFragment>()) {
        viewModel { HistoryViewModel( get() ) }
        scoped { HistoryInteractor( get(named(NAME_REMOTE)), get(named(NAME_LOCAL)) )}
    }
}

val imageScreen = module {
        viewModel { ImageViewModel( get() ) }
        factory { ImageInteractor( get() ) }
}

val favoriteScreen = module {
    scope(named<FavoriteFragment>()) {
        viewModel { FavoriteViewModel( get() )}
        scoped { FavoriteInteractor( get() )}
    }
}