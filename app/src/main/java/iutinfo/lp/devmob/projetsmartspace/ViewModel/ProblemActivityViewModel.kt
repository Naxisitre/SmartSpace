package iutinfo.lp.devmob.projetsmartspace.ViewModel

import android.graphics.Bitmap
import android.net.Uri
import android.util.Base64
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import iutinfo.lp.devmob.projetsmartspace.API.GetDataService
import iutinfo.lp.devmob.projetsmartspace.API.ProblemInfo
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.ByteArrayOutputStream

class ProblemActivityViewModel: ViewModel() {

    val myResponse: MutableLiveData<ProblemInfo> = MutableLiveData()

    fun postProblem(image: MultipartBody.Part, identifiant: RequestBody, rapport: RequestBody) {
        viewModelScope.launch() {
            try {
                myResponse.value = GetDataService.retrofit.postProblem(image, identifiant, rapport)
            } catch (e: Exception) {
            }
        }
    }
}