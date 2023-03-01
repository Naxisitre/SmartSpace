package iutinfo.lp.devmob.smartspace.API

import retrofit2.http.GET

interface DataService {
    @GET("last")
    suspend fun getData(): Data
}