package iutinfo.lp.devmob.smartspace

import android.app.PendingIntent
import android.content.Context
import android.nfc.NfcAdapter
import android.util.AttributeSet
import android.widget.FrameLayout


class ButtonProbView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    init {
        inflate(context, R.layout.boutonprob_view, this)
    }
}