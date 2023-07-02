package gb.com.model.datasource.api

import gb.com.model.data.wordImage.SkyengWord
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface SkyengApiService {
    @GET("words/search")
    fun searchWord(
        @Query("search") query: String
    ) : Deferred<List<SkyengWord>>

}