package iutinfo.lp.devmob.smartspace.API

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GetDataService {
val retrofit: DataService by lazy {
    val client = OkHttpClient.Builder().build()
    Retrofit.Builder()
        .baseUrl("http://smartspace.lp-cloud.tech/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(DataService::class.java)
}
}