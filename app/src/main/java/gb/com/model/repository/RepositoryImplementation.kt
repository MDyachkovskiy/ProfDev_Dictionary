package gb.com.model.repository

import gb.com.model.data.WordDefinition
import gb.com.model.datasource.DataSource

class RepositoryImplementation(
    private val dataSource: DataSource<List<WordDefinition>>
) : Repository<List<WordDefinition>>{

    override suspend fun getData(word: String): List<WordDefinition> {
        return dataSource.getData(word)
    }
}