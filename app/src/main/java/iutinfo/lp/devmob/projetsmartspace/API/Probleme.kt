package iutinfo.lp.devmob.projetsmartspace.API

import com.google.gson.annotations.SerializedName

data class Probleme(
    @SerializedName("image") val image: String,
    @SerializedName("Identifiant") val identifiant: String,
    @SerializedName("Rapport") val rapport: String,
)
