package iutinfo.lp.devmob.smartspace

import android.app.PendingIntent
import android.nfc.NfcAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class IdentificationActivity : AppCompatActivity() {
    var nfcAdapter: NfcAdapter? = null
    var pendingIntent: PendingIntent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_identification)

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
    }
}