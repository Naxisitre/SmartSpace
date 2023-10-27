package iutinfo.lp.devmob.projetsmartspace.API

data class GetProblem(
    var id: String? = null,
    var Identifiant: String? = null,
    var prenom: String? = null,
    var nom: String? = null,
    var Rapport: String? = null,
    var Titre: String? = null,
    var urlimage: String? = null,
    var vu: String? = null,
    var etat: String? = null,
    var createdAt: String? = null,
)
