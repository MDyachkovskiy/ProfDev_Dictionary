package gb.com.presenter

import gb.com.model.data.AppState
import gb.com.model.data.WordDefinition
import gb.com.model.repository.Repository
import io.reactivex.Observable

class MainInteractor(
    private val remoteRepository: Repository<List<WordDefinition>>
) : Interactor<AppState> {

    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
        return remoteRepository.getData(word).map{ AppState.Success(it) }
    }
}