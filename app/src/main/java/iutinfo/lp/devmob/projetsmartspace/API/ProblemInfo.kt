package iutinfo.lp.devmob.projetsmartspace.API

import android.graphics.Bitmap
import android.net.Uri
import java.io.File

data class ProblemInfo(
    var userId: String ?= null,
    var uri: Uri? = null,
    var textDesc: String? = null,
    var bitmap: Bitmap? = null,
    var photoFile: File? = null,
    var titre: String? = null
)
