package gb.com.model.datasource

import gb.com.model.data.WordDefinition
import gb.com.model.datasource.api.RetrofitImplementation

class DataSourceRemote(
    private val remoteProvider: RetrofitImplementation = RetrofitImplementation()
) : DataSource<List<WordDefinition>> {

    override fun getData(word: String) = remoteProvider.getData(word)
}
