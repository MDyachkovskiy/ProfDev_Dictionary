package gb.com.model.datasource.api

import gb.com.model.data.WordDefinition
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("{word}")
    fun getWordDefinition(
        @Path("word") wordToSearch: String
    ) : Observable<List<WordDefinition>>
}