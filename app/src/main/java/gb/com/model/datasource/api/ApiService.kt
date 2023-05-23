package gb.com.model.datasource.api

import gb.com.model.data.WordDefinition
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("{word}")
    fun getWordDefinition(
        @Path("word") wordToSearch: String
    ) : Deferred<List<WordDefinition>>
}