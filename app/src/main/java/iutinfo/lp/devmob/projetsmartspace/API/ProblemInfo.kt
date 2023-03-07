package iutinfo.lp.devmob.projetsmartspace.API

import android.net.Uri
import okhttp3.MultipartBody

data class ProblemInfo(
    val image: MultipartBody.Part? = null,
    val identifiant: String? = null,
    val rapport: String? = null,

)
