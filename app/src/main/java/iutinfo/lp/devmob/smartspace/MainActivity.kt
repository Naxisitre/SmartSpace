package iutinfo.lp.devmob.smartspace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import iutinfo.lp.devmob.projetsmartspace.Composants.LightView
import iutinfo.lp.devmob.projetsmartspace.Composants.TemperatureView
import iutinfo.lp.devmob.projetsmartspace.R

class MainActivity : AppCompatActivity() {

    private var handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        //findViewById<TemperatureView>(R.id.temperatueAmbiante).setTemp("24")

        //findViewById<LightView>(R.id.etatLumiere).setLight("On")
        //findViewById<HeaderView>(R.id.headerView).setName("Alexis")
        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        refreshData(viewModel);
        refreshDataLoop(viewModel);
    }

    private fun refreshDataLoop(viewModel: MainActivityViewModel) {
        handler.postDelayed({
            refreshData(viewModel)
            refreshDataLoop(viewModel)
        }, resources.getInteger(R.integer.refresh_time).toLong());
    }

    private fun refreshData(viewModel: MainActivityViewModel) {
        viewModel.getData()
        Log.i("Error", viewModel.error.toString())
        if(viewModel.error){
                findViewById<TemperatureView>(R.id.temperatureAmbiante).setTemp(null, "Error")
                findViewById<LightView>(R.id.etatLumiere).setLight("Error", "Error")
        }
        else {
            viewModel.myResponse.observe(this, Observer {
                if (it != null) {
                    val temp = (it.Temperature).subSequence(0, 2).toString().toInt()
                    val hour = (it.createdAt).subSequence(11, 13).toString().toInt() + 1
                    val minutes = (it.createdAt).subSequence(14, 16).toString()
                    findViewById<TemperatureView>(R.id.temperatureAmbiante).setTemp(temp, "$hour h $minutes")
                    findViewById<LightView>(R.id.etatLumiere).setLight(it.Lumiere,"$hour h $minutes")
                }
            })
        }
    }
}