package iutinfo.lp.devmob.projetsmartspace.Composants

import android.content.Context
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import iutinfo.lp.devmob.projetsmartspace.API.GetProblem
import iutinfo.lp.devmob.projetsmartspace.R

class ProblemView(context: Context): FrameLayout(context) {

    init {
        inflate(context, R.layout.view_problem, this)
    }
    fun populateEnCours(getProblem: GetProblem?){
        if (getProblem?.etat == "En cours") {
            findViewById<TextView>(R.id.title).text =
                resources.getString(R.string.title_vue_prob, getProblem.Titre)
            findViewById<TextView>(R.id.signaledBy).text = resources.getString(
                R.string.info_report,
                getProblem.prenom + " " + getProblem.nom
            )
            findViewById<TextView>(R.id.signaledAt).text =
                resources.getString(R.string.signal_at, getProblem.createdAt)
        } else {
            findViewById<LinearLayout>(R.id.vue_probleme).visibility = GONE
        }
    }
    fun populateATraiter(getProblem: GetProblem?) {
        if (getProblem?.etat == "à traiter") {
            findViewById<TextView>(R.id.title).text =
                resources.getString(R.string.title_vue_prob, getProblem.Titre)
            findViewById<TextView>(R.id.signaledBy).text = resources.getString(
                R.string.info_report,
                getProblem.prenom + " " + getProblem.nom
            )
            findViewById<TextView>(R.id.signaledAt).text =
                resources.getString(R.string.signal_at, getProblem.createdAt)
        }
    }
}