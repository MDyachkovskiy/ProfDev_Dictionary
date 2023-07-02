package gb.com.presenter

import gb.com.model.data.wordDefinition.AppState
import gb.com.model.repository.RepositoryFavorites

class FavoriteInteractor(
    private val favoriteRepository: RepositoryFavorites
) : Interactor<AppState>{

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        val data = favoriteRepository.getAllFavorites()
        return AppState.Success(data)
    }
}