package gb.com.model.repository

import gb.com.model.data.WordDefinition
import gb.com.model.datasource.DataSource
import io.reactivex.Observable

class RepositoryImplementation(
    private val dataSource: DataSource<List<WordDefinition>>
) : Repository<List<WordDefinition>>{

    override fun getData(word: String): Observable<List<WordDefinition>> {
        return dataSource.getData(word)
    }
}