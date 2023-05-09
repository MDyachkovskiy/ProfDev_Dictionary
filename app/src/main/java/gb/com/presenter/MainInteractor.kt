package gb.com.presenter

import gb.com.di.NAME_REMOTE
import gb.com.model.data.AppState
import gb.com.model.data.WordDefinition
import gb.com.model.repository.Repository
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Named

class MainInteractor @Inject
constructor(
    @Named(NAME_REMOTE) val remoteRepository: Repository<List<WordDefinition>>
) : Interactor<AppState> {

    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
        return remoteRepository.getData(word).map{ AppState.Success(it) }
    }
}