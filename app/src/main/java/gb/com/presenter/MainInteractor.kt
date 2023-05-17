package gb.com.presenter

import gb.com.model.data.AppState
import gb.com.model.data.WordDefinition
import gb.com.model.repository.Repository
class MainInteractor(
    private val remoteRepository: Repository<List<WordDefinition>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(remoteRepository.getData(word))
    }
}