package gb.com.model.repository

import gb.com.model.data.AppState
import gb.com.model.data.WordDefinition
import gb.com.model.datasource.DataSourceLocal

class RepositoryImplementationLocal(
    private val dataSource: DataSourceLocal<List<WordDefinition>>
) : RepositoryLocal <List<WordDefinition>>{
    override suspend fun saveToDB(appState: AppState) {
        dataSource.saveToDB(appState)
    }

    override suspend fun getData(word: String): List<WordDefinition> {
        return dataSource.getData(word)
    }

}