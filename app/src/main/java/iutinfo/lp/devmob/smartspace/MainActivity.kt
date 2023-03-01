package iutinfo.lp.devmob.smartspace


import android.app.PendingIntent
import android.nfc.NfcAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private var handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)


        refreshData(viewModel)
        refreshDataLoop(viewModel)
    }

    private fun refreshDataLoop(viewModel: MainActivityViewModel) {
        handler.postDelayed({
            refreshData(viewModel)
            refreshDataLoop(viewModel)
        }, resources.getInteger(R.integer.refresh_time).toLong())
    }

    private fun refreshData(viewModel: MainActivityViewModel) {
        viewModel.getData()
        if(viewModel.error){
                findViewById<TemperatureView>(R.id.temperatureAmbiante).setTemp(null, "Error")
                findViewById<LightView>(R.id.etatLumiere).setLight("Error", "Error")

        }
        viewModel.myResponse.observe(this) {
            if (it != null) {
                val temp = (it.Temperature).subSequence(0, 2).toString().toInt()
                val hour = (it.createdAt).subSequence(11, 13).toString().toInt() + 1
                val minutes = (it.createdAt).subSequence(14, 16).toString()
                findViewById<TemperatureView>(R.id.temperatureAmbiante).setTemp(
                    temp,
                    "$hour h $minutes"
                )
                findViewById<LightView>(R.id.etatLumiere).setLight(it.Lumiere, "$hour h $minutes")
            }
        }
    }

}