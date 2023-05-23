package gb.com.model.datasource
interface DataSource<T> {

    suspend fun getData(word: String) : T
}