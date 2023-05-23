package gb.com.presenter

interface Interactor<T> {

    suspend fun getData(word: String, fromRemoteSource: Boolean): T

}