package gb.com.presenter

import gb.com.model.data.wordDefinition.AppState
import gb.com.model.data.wordDefinition.WordDefinition
import gb.com.model.repository.Repository
import gb.com.model.repository.RepositoryLocal

class MainInteractor(
    private val remoteRepository: Repository<List<WordDefinition>>,
    private val localRepository: RepositoryLocal<List<WordDefinition>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        val appState: AppState
        if (fromRemoteSource) {
            appState = AppState.Success(remoteRepository.getData(word))
            localRepository.saveToDB(appState)
        } else {
            appState = AppState.Success(localRepository.getData(word))
        }
        return appState
    }
}