package iutinfo.lp.devmob.smartspace

import retrofit2.http.GET

interface DataService {
    @GET("last")
    suspend fun getData(): Data
}