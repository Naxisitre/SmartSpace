package iutinfo.lp.devmob.projetsmartspace


import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.nfc.NfcAdapter
import android.os.*
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.view.View.GONE
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import iutinfo.lp.devmob.projetsmartspace.API.Notification
import iutinfo.lp.devmob.projetsmartspace.Composants.LightView
import iutinfo.lp.devmob.projetsmartspace.Composants.TemperatureView
import iutinfo.lp.devmob.projetsmartspace.ViewModel.MainActivityViewModel
import iutinfo.lp.devmob.projetsmartspace.ViewModel.ProblemActivityViewModel

class MainActivity : AppCompatActivity() {

    private var nfcAdapter: NfcAdapter? = null

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

    fun onProblemClicked(view: View) {
        val dialog = AlertDialog.Builder(this).setView(R.layout.popupnfc_view).show()
        var intentNFC: Intent?
        try {


        if(!nfcAdapter!!.isEnabled){
            dialog.findViewById<TextView>(R.id.title).text = resources.getString(R.string.nfc_text_state, "désactivé")
            dialog.findViewById<TextView>(R.id.text_nfc).text = resources.getString(R.string.info_comp_nfc, "Vous devez activer le NFC pour vous identifier avec votre carte izly")
            dialog.findViewById<TextView>(R.id.active_nfc).text = resources.getString(R.string.active_nfc, "Activer le NFC")
            dialog.findViewById<TextView>(R.id.active_nfc).setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
                     intentNFC = Intent(android.provider.Settings.ACTION_NFC_SETTINGS)
                     startActivity(intentNFC)
                }
                else {
                    intentNFC = Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(intentNFC)
                }
                dialog.dismiss()
            }
        }
        else if(nfcAdapter!!.isEnabled){
            dialog.findViewById<TextView>(R.id.title).text = resources.getString(R.string.nfc_text_state, "activé")
            dialog.findViewById<TextView>(R.id.text_nfc).text = resources.getString(R.string.info_comp_nfc, "Identifiez vous en cliquant sur le bouton en dessous pour signaler un problème")
            dialog.findViewById<TextView>(R.id.active_nfc).text = resources.getString(R.string.active_nfc, "Vous identifiez")
            dialog.findViewById<TextView>(R.id.active_nfc).setOnClickListener {
                val intentActitity = Intent(this, AuthentificationActivity::class.java)
                startActivity(intentActitity)
                dialog.dismiss()
            }
        }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()

            // Post
            val notification = Notification(token.toString(),"test")
            val viewModel = ViewModelProvider(this).get(ProblemActivityViewModel::class.java)
            viewModel.postNotif(token, "test")

        })
    }
}