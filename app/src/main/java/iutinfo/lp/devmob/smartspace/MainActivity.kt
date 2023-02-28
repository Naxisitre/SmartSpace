package iutinfo.lp.devmob.smartspace


import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Intent
import android.nfc.NfcAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private var nfcAdapter: NfcAdapter? = null
    private var pendingIntent: PendingIntent? = null
    private var handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        //this.nfcAdapter = NfcAdapter.getDefaultAdapter(this)
       // pendingIntent = PendingIntent.getActivity(this, 0, Intent(this, this.javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0)

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
        if(viewModel.error){
                findViewById<TemperatureView>(R.id.temperatureAmbiante).setTemp(null, "Error")
                findViewById<LightView>(R.id.etatLumiere).setLight("Error", "Error")

        }
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

    fun onProblemClicked(view: View) {
        AlertDialog.Builder(this).setView(R.layout.popupnfc_view).show()
    }

}