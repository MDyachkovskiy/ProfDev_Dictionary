package gb.com.model.datasource.api
interface DataSource<T> {

    suspend fun getData(word: String) : T
}