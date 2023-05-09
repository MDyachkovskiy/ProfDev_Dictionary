package gb.com.model.datasource.db

import gb.com.model.data.WordDefinition
import gb.com.model.datasource.DataSource
import io.reactivex.Observable

class RoomDataBaseImplementation : DataSource<List<WordDefinition>> {

    override fun getData(word: String): Observable<List<WordDefinition>> {
        TODO("not implemented")
    }
}