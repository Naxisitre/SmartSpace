package iutinfo.lp.devmob.projetsmartspace.ViewModel

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import iutinfo.lp.devmob.projetsmartspace.API.GetDataService
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
    lateinit var report: ProblemInfo
    var call: Call<ProblemInfo?>? = null
    var response: Response<ProblemInfo?>? = null

    fun postProblem(): Boolean {
        var bool: Boolean = true
        if(userId == null ||uri == null ||textDesc == null || bitmap == null || photoFile == null ) {
            bool = false
        }
        report = ProblemInfo(userId!!, uri!!, textDesc!!, bitmap!!, photoFile!!)
        viewModelScope.launch() {
            try {
                val requestFile: RequestBody = RequestBody.create(
                    MediaType.parse("image/jpg"),
                    photoFile!!
                )
                Log.i("Request", requestFile.toString())
                call = GetDataService.retrofit.postProblem(
                    MultipartBody.Part.createFormData("image", photoFile!!.name, requestFile),
                    RequestBody.create(MediaType.parse("text/plain"), userId!!),
                    RequestBody.create(MediaType.parse("text/plain"), textDesc!!)
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
            }
        }
        return bool
    }
}