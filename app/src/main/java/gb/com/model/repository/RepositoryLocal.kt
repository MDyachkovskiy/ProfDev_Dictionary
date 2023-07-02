package gb.com.model.repository

import gb.com.model.data.wordDefinition.AppState

interface RepositoryLocal<T> : Repository<T> {
    suspend fun saveToDB(appState: AppState)
}