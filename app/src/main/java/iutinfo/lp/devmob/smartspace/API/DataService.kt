package iutinfo.lp.devmob.smartspace.API

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface DataService {
    @GET("mesures/last")
    suspend fun getData(): Data

    @GET("user/all")
    suspend fun getAllID(): List<IDUsers>

    @Headers("Content-Type: application/json")
    @POST("user/add")
    suspend fun addUser(@Body userId: String): UserInfo

    @Headers("Content-Type: application/json")
    @POST("user/login")
    suspend fun testLogin(@Body userId: String): UserInfo

}