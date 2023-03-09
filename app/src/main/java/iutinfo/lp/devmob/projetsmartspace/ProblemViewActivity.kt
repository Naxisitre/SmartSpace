package iutinfo.lp.devmob.projetsmartspace

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import androidx.lifecycle.ViewModelProvider
import iutinfo.lp.devmob.projetsmartspace.API.GetDataService
import iutinfo.lp.devmob.projetsmartspace.ViewModel.ProblemViewActivityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProblemViewActivity : AppCompatActivity() {
    var viewModel = ProblemViewActivityViewModel()
    var listViewEnCours: ListView? = null
    var listViewAtraiter: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_problem_view)
        supportActionBar?.hide()
        viewModel = ViewModelProvider(this).get(ProblemViewActivityViewModel::class.java)
        listViewEnCours = findViewById(R.id.listView_encours)
        listViewAtraiter = findViewById(R.id.listView_atraiter)
        executeCall(this)
    }

    private fun executeCall(context: Context) {
        GlobalScope.launch(Dispatchers.Main){
            try {
                val myResponse = GetDataService.retrofit.getAllProblems()
                if(myResponse.isSuccessful){
                    val responseBody = myResponse.body()
                    Log.i("Response", responseBody.toString())
                    if(responseBody != null) {
                        for (i in responseBody) {
                            if (i.etat == "à traiter") {
                                    listViewAtraiter?.adapter = ProblemViewAdapter(context, responseBody)
                            } else if (i.etat == "En Cours") {
                                    listViewEnCours?.adapter = ProblemViewAdapter(context, responseBody)
                                }
                            }
                        }
                    }
                }
            catch (e: Exception) {
                e.printStackTrace()
        }
    }
}
    fun retourAcceuil(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}