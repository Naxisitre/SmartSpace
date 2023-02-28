package iutinfo.lp.devmob.smartspace

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

class PopupNFCView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    init {
        inflate(context, R.layout.popupnfc_view, this)
    }
}
