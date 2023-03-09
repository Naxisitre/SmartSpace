package iutinfo.lp.devmob.projetsmartspace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import iutinfo.lp.devmob.projetsmartspace.ViewModel.ProblemViewActivityViewModel

class ProblemViewActivity : AppCompatActivity() {
    var viewModel = ProblemViewActivityViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_problem_view)
        supportActionBar?.hide()
        viewModel = ViewModelProvider(this).get(ProblemViewActivityViewModel::class.java)
        viewModel.listViewEnCours = findViewById(R.id.listView_encours)
        viewModel.listViewAtraiter = findViewById(R.id.listView_atraiter)
        viewModel.getProblem()
        viewModel.context = this
    }

    fun retourAcceuil(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}