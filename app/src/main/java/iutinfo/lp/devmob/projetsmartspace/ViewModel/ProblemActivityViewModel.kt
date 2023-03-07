package iutinfo.lp.devmob.projetsmartspace.ViewModel

import android.graphics.Bitmap
import android.net.Uri
import android.util.Base64
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import iutinfo.lp.devmob.projetsmartspace.API.ProblemInfo
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Response
import java.io.ByteArrayOutputStream

class ProblemActivityViewModel: ViewModel() {
    var Identifiant: String ? = null
    var Rapport: String ? = null
    var image: Bitmap? = null
    var uri: Uri? = null

    lateinit var report: ProblemInfo
    var call: ProblemInfo? = null
    var response: Response<ProblemInfo?>? = null
    val myResponse: MutableLiveData<ProblemInfo> = MutableLiveData()

    fun postProblem():Boolean {
        var bool: Boolean = true
        if (Identifiant == null || Rapport == null || image == null || uri == null) {
            bool = false
        }
        report = ProblemInfo(image!!, Identifiant!!, Rapport!!, uri!!)
        viewModelScope.launch() {
            try {
                val baos = ByteArrayOutputStream()
                report.image.compress(Bitmap.CompressFormat.PNG, 100, baos)
                val b = baos.toByteArray()
                val imageEncoded = Base64.encodeToString(b, Base64.DEFAULT)
                val requestBody: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data; charset=utf-8"), imageEncoded)

                //call = GetDataService.retrofit.uploadReport(requestBody, identifiant, rapport)
            } catch (e: Exception) {
            }
        }
        return bool
    }
}