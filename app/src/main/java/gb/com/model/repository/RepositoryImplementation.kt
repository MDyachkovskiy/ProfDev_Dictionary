package gb.com.model.repository

import gb.com.model.data.wordDefinition.WordDefinition
import gb.com.model.data.wordImage.SkyengWord
import gb.com.model.datasource.api.DataSource

class RepositoryImplementation(
    private val dataSource: DataSource<List<WordDefinition>>,
    private val skyengDataSource: DataSource<List<SkyengWord>>
) : Repository<List<WordDefinition>>, RepositorySkyeng {

    override suspend fun getData(word: String): List<WordDefinition> {
        return dataSource.getData(word)
    }

    override suspend fun getSkyengWord(word: String): List<SkyengWord> {
        return skyengDataSource.getData(word)
    }
}