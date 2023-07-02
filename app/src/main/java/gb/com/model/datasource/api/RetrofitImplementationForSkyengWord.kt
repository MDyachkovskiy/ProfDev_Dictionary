package gb.com.model.datasource.api

import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import gb.com.model.data.wordImage.SkyengWord
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitImplementationForSkyengWord : DataSource<List<SkyengWord>> {

    override suspend fun getData(word: String): List<SkyengWord> {
        val response = getSkyengService(BaseInterceptor.interceptor).searchWord(word).await()
        Log.d("@@@", "Response: $response")
        return response
    }

    private fun getSkyengService(interceptor: Interceptor) : SkyengApiService {
        return createRetrofit(interceptor).create(SkyengApiService::class.java)
    }

    private fun createRetrofit(interceptor: Interceptor) : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dictionary.skyeng.ru/api/public/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(createOkHttpClient(interceptor))
            .build()
    }

    private fun createOkHttpClient(interceptor: Interceptor) : OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()
    }
}