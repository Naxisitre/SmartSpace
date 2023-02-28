package iutinfo.lp.devmob.smartspace

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView


class PopupNFCView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    init {
        inflate(context, R.layout.popupnfc_view, this)
        findViewById<TextView>(R.id.title).text = resources.getString(R.string.nfc_text_state, "NFC est désactivé")
        findViewById<TextView>(R.id.text_nfc).text = resources.getString(R.string.info_comp_nfc, "Vous devez activer le NFC pour vous identifier avec votre carte izly")
    }
}