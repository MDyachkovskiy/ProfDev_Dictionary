package gb.com.presenter

import gb.com.model.data.wordDefinition.AppState
import gb.com.model.data.wordDefinition.WordDefinition
import gb.com.model.repository.Repository
import gb.com.model.repository.RepositoryLocal

class HistoryInteractor(
    private val repositoryRemote: Repository<List<WordDefinition>>,
    private val repositoryLocal: RepositoryLocal<List<WordDefinition>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(
            if (fromRemoteSource){
                repositoryRemote
            } else {
                repositoryLocal
            }.getData(word)
        )
    }
}