package iutinfo.lp.devmob.smartspace.API

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("identifiant") val userId: String?,
)
