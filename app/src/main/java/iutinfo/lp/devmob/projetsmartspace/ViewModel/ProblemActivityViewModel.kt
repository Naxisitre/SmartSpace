package iutinfo.lp.devmob.projetsmartspace.ViewModel

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import iutinfo.lp.devmob.projetsmartspace.API.GetDataService
import iutinfo.lp.devmob.projetsmartspace.API.Notification
import iutinfo.lp.devmob.projetsmartspace.API.ProblemInfo
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProblemActivityViewModel: ViewModel() {
    var userId: String ?= null
    var uri: Uri? = null
    var textDesc: String? = null
    var bitmap: Bitmap? = null
    var photoFile: File? = null
    var titre: String? = null
    var token: String? = null
    lateinit var report: ProblemInfo
    var call: Call<ProblemInfo?>? = null
    var response: Response<ProblemInfo?>? = null

    val myResponse: MutableLiveData<Notification> = MutableLiveData()
    var error : Boolean = false
    var errorTime : Int = 0

    fun postProblem(): Boolean {
        var bool: Boolean = true
        if(userId == null ||uri == null ||textDesc == null || bitmap == null || photoFile == null || titre == null) {
            bool = false
        }
        report = ProblemInfo(userId!!, uri!!, textDesc!!, bitmap!!, photoFile!!, titre!!)
        viewModelScope.launch() {
            try {
                Log.i("Test", "ça marche")
                val requestFile: RequestBody = RequestBody.create(
                    MediaType.parse("image/jpg"),
                    photoFile!!
                )
                Log.i("Request", requestFile.toString())
                call = GetDataService.retrofit.postProblem(
                    MultipartBody.Part.createFormData("image", photoFile!!.name, requestFile),
                    RequestBody.create(MediaType.parse("text/plain"), userId!!),
                    RequestBody.create(MediaType.parse("text/plain"), textDesc!!),
                    RequestBody.create(MediaType.parse("text/plain"), titre!!),
                    RequestBody.create(MediaType.parse("text/plain"), token!!)
                )
                call!!.enqueue(object : Callback<ProblemInfo?> {
                    override fun onResponse(
                        call: Call<ProblemInfo?>,
                        response: Response<ProblemInfo?>
                    ) {
                        Log.i("Response", response.body().toString())
                        Log.i("Response", response.code().toString())
                    }

                    override fun onFailure(call: Call<ProblemInfo?>, t: Throwable) {
                        Log.i("Response", call.toString())
                        t.printStackTrace()
                    }
                })
        }
            catch (e: Exception) {
                bool = false
                Log.i("Response", "ça marche pas ")
                Log.i("Response", response?.code().toString())
            }
        }
        return bool
    }

    /*fun postNotif(token : String, message : String) {
        viewModelScope.launch() {
            try {
                val notification = Notification(token, message)
                myResponse.value = GetDataService.retrofit.sendNotification(notification)
                error = false
                errorTime = 0
            } catch (e: Exception) {
                errorTime++
                error = true
            }
        }
    }*/

}