package iutinfo.lp.devmob.projetsmartspace.API

import android.graphics.Bitmap
import android.net.Uri
import com.google.gson.annotations.SerializedName

data class ProblemInfo(
    @SerializedName("image") val image: Bitmap,
    @SerializedName("Identifiant") val identifiant: String,
    @SerializedName("Rapport") val rapport: String,
    val uri: Uri
)
