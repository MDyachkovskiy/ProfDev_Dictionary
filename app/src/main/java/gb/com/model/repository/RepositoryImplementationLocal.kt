package gb.com.model.repository

import gb.com.model.data.wordDefinition.AppState
import gb.com.model.data.wordDefinition.WordDefinition
import gb.com.model.datasource.db.DataSourceLocal

class RepositoryImplementationLocal(
    private val dataSource: DataSourceLocal<List<WordDefinition>>
) : RepositoryLocal <List<WordDefinition>> {
    override suspend fun saveToDB(appState: AppState) {
        dataSource.saveToDB(appState)
    }

    override suspend fun getData(word: String): List<WordDefinition> {
        return dataSource.getData(word)
    }
}