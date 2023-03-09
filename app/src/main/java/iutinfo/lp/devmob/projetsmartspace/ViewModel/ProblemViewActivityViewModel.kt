package iutinfo.lp.devmob.projetsmartspace.ViewModel

import android.content.Context
import android.util.Log
import android.widget.ListView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import iutinfo.lp.devmob.projetsmartspace.API.GetDataService
import iutinfo.lp.devmob.projetsmartspace.ProblemViewAdapter
import kotlinx.coroutines.launch


class ProblemViewActivityViewModel: ViewModel() {
    var listViewEnCours: ListView? = null
    var listViewAtraiter: ListView? = null
    var context: Context? = null
    var etat: String? = null
    fun getProblem() {
        try {
            viewModelScope.launch {
                val myResponse = GetDataService.retrofit.getAllProblems()
                val responseBody = myResponse.body()
                Log.i("Response", responseBody.toString())
                if(responseBody != null) {
                    listViewEnCours?.adapter = context?.let { ProblemViewAdapter(it, responseBody) }
                    /*for (i in responseBody) {
                        etat = i.etat
                    }*/
                    //Log.i("ResponseEtat", responseBody[0].etat.toString())
                    /*when (etat) {
                        "à traiter" -> {
                            listViewAtraiter?.adapter =
                                context?.let { ProblemViewAdapter(it, responseBody) }
                        }
                        "en cours" -> {
                            listViewEnCours?.adapter =
                                context?.let { ProblemViewAdapter(it, responseBody) }
                        }
                    }*/
                }
                    //listViewEnCours?.adapter = context?.let { ProblemViewAdapter(it, responseBody) }
                }
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }
}