package gb.com.presenter

import gb.com.model.data.wordDefinition.AppState
import gb.com.model.repository.RepositorySkyeng

class ImageInteractor(
    private val remoteRepository: RepositorySkyeng
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(remoteRepository.getSkyengWord(word))
    }
}