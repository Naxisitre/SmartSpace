package iutinfo.lp.devmob.projetsmartspace.API

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("identifiant") val userId: String?,
)
