package iutinfo.lp.devmob.smartspace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

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
        viewModel.myResponse.observe(this, Observer {
            val temp = (it.Temperature).subSequence(0, 2).toString().toInt()
            findViewById<TemperatureView>(R.id.temperatureAmbiante).setTemp(temp)
            findViewById<LightView>(R.id.etatLumiere).setLight(it.Lumiere)
        })
    }

}