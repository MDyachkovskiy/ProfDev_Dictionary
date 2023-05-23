package gb.com.model.datasource.db

import gb.com.model.data.WordDefinition
import gb.com.model.datasource.DataSource
class RoomDataBaseImplementation : DataSource<List<WordDefinition>> {

    override suspend fun getData(word: String): List<WordDefinition> {
        TODO("not implemented")
    }
}