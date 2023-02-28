package iutinfo.lp.devmob.smartspace


import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View.GONE
import android.widget.TextView
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

        this.nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        if (nfcAdapter == null) {
            findViewById<TextView>(R.id.button_prob).visibility = GONE
        }
        /*pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, this.javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            PendingIntent.FLAG_IMMUTABLE
        )*/
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

    fun onProblemClicked() {
        val dialog = AlertDialog.Builder(this).setView(R.layout.popupnfc_view).show()
        var intent: Intent?
        if(!nfcAdapter!!.isEnabled){
            dialog.findViewById<TextView>(R.id.title).text = resources.getString(R.string.nfc_text_state, "désactivé")
            dialog.findViewById<TextView>(R.id.text_nfc).text = resources.getString(R.string.info_comp_nfc, "Vous devez activer le NFC pour vous identifier avec votre carte izly")
            dialog.findViewById<TextView>(R.id.active_nfc).text = resources.getString(R.string.active_nfc, "Activer le NFC")
            dialog.findViewById<TextView>(R.id.active_nfc).setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
                     intent = Intent(android.provider.Settings.ACTION_NFC_SETTINGS)
                    startActivity(intent)
                }
                else {
                    intent = Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(intent)
                }
            }
        }
        else if(nfcAdapter!!.isEnabled){
            dialog.findViewById<TextView>(R.id.title).text = resources.getString(R.string.nfc_text_state, "activé")
            dialog.findViewById<TextView>(R.id.text_nfc).text = resources.getString(R.string.info_comp_nfc, "placer votre carte izly sur votre téléphone pour vous identifier")
            dialog.findViewById<TextView>(R.id.active_nfc).visibility = GONE
        }
    }

}