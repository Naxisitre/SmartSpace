package iutinfo.lp.devmob.smartspace

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataNetwork {
val retrofit by lazy {
    Retrofit.Builder()
        .baseUrl("http://smartspace.lp-cloud.tech/api/mesures/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(DataService::class.java)
}
}