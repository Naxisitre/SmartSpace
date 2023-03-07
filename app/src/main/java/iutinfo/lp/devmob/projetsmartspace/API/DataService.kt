package iutinfo.lp.devmob.projetsmartspace.API

import okhttp3.RequestBody
import retrofit2.http.*

interface DataService {
    @GET("mesures/last")
    suspend fun getData(): Data

    @GET("user/all")
    suspend fun getAllID(): List<IDUsers>

    @Headers("Content-Type: application/json")
    @POST("user/add")
    suspend fun addUser(@Body userId: String): UserInfo

    @Multipart
    @POST("report/upload")
    suspend fun uploadReport(@Part("image")  picture: RequestBody, @Part("Identifiant") Identifiant: String?, @Part("Rapport") Rapport: String): ProblemInfo?

    @Headers("Content-Type: application/json")
    @POST("user/login")
    suspend fun testLogin(@Body userId: String): UserInfo

}