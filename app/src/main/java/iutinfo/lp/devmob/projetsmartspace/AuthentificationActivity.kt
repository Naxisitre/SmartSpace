package iutinfo.lp.devmob.projetsmartspace

import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import iutinfo.lp.devmob.projetsmartspace.ViewModel.AuthentificationActivityViewModel
import kotlin.experimental.and

class AuthentificationActivity : AppCompatActivity() {

    private var nfcAdapter: NfcAdapter? = null
    private var pendingIntent: PendingIntent? = null
    var idHex : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentification_view)
        supportActionBar?.hide()



        this.nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, this.javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            PendingIntent.FLAG_MUTABLE
        )
    }

    override fun onResume() {
        super.onResume()
        //assert(nfcAdapter != null)
        //nfcAdapter.enableForegroundDispatch(context,pendingIntent,
        //                                    intentFilterArray,
        //                                    techListsArray)
        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, null, null)
    }

    override fun onPause() {
        super.onPause()
        //Onpause stop listening
        if (nfcAdapter != null) {
            nfcAdapter!!.disableForegroundDispatch(this)
        }
    }

    private fun resolveIntent(intent: Intent) {
        val viewModel = ViewModelProvider(this).get(AuthentificationActivityViewModel::class.java)
        val action = intent.action
        if (NfcAdapter.ACTION_TAG_DISCOVERED == action || NfcAdapter.ACTION_TECH_DISCOVERED == action || NfcAdapter.ACTION_NDEF_DISCOVERED == action) {
            val tag: Tag = (intent.getParcelableExtra<Parcelable>(NfcAdapter.EXTRA_TAG) as Tag?)!!
            val id = tag.id
            idHex = toHex(id)

            Log.i("IDSEND", "Tag ID: $idHex")
            viewModel.getIDUsers()
            //postID(idHex, viewModel)
            var bool = true
            if(viewModel.error){
                val dialog = AlertDialog.Builder(this).setView(R.layout.popupnfc_view).show()
                dialog.findViewById<TextView>(R.id.title).text = resources.getString(R.string.errorServer)
                dialog.findViewById<TextView>(R.id.text_nfc).visibility = View.GONE
                dialog.findViewById<TextView>(R.id.active_nfc).visibility = View.GONE

            }
            viewModel.myResponseList.observe(this){
                if(it != null){
                    for (i in it){
                        Log.i("ALLID", "ID USER: $it")
                        Log.i("IDGET", "ID USER: ${i!!.identifiant}")
                        if(i.identifiant == idHex){
                            val intentActivity = Intent(this, ProblemeActivity::class.java)
                            intentActivity.putExtra("userID", idHex)
                            startActivity(intentActivity)
                        } else {
                            bool = false
                        }
                    }
                    if(!bool){
                        val dialog = AlertDialog.Builder(this).setView(R.layout.popupnfc_view).show()
                        dialog.findViewById<TextView>(R.id.title).text = resources.getString(R.string.unknowUser)
                        dialog.findViewById<TextView>(R.id.text_nfc).visibility = View.GONE
                        dialog.findViewById<TextView>(R.id.active_nfc).visibility = View.GONE
                        dialog.setOnDismissListener{
                            val intentActivity = Intent(this, MainActivity::class.java)
                            startActivity(intentActivity)
                            dialog.cancel()
                        }
                        bool = true
                    }
                }
            }
            }
        }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        resolveIntent(intent)
    }


    private fun toHex(bytes: ByteArray): String {
        val sb = StringBuilder()
        for (i in bytes.indices.reversed()) {
            val b: Byte = bytes[i] and 0xff.toByte()
            if (b < 0x10) sb.append('0')
            sb.append(Integer.toHexString(b.toInt()))
            if (i > 0) {
                sb.append(" ")
            }
        }
        return sb.toString()
    }

    fun retourAcceuil(view: View) {
        val intentAcceuil = Intent(this, MainActivity::class.java)
        startActivity(intentAcceuil)
    }
}