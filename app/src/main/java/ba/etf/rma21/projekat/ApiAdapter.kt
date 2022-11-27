package ba.etf.rma21.projekat

import ba.etf.rma21.projekat.data.repositories.ApiConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiAdapter {
    private var apiConfig: ApiConfig = ApiConfig()

    val retrofit : Api = Retrofit.Builder()
        .baseUrl(apiConfig.getUrl())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api::class.java)
}