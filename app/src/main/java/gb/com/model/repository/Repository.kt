package gb.com.model.repository

interface Repository<T> {
    suspend fun getData(word: String): T
}