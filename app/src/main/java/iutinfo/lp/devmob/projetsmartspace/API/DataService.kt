package iutinfo.lp.devmob.projetsmartspace.API

import okhttp3.MultipartBody
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
    suspend fun postProblem(@Part image: MultipartBody.Part, @Part("Identifiant") Identifiant: RequestBody, @Part("Rapport") Rapport: RequestBody): ProblemInfo?

    @Headers("Content-Type: application/json")
    @POST("user/login")
    suspend fun testLogin(@Body userId: String): UserInfo

}