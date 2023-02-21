package iutinfo.lp.devmob.smartspace

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat


class TemperatureView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    init {
        inflate(context, R.layout.temperature_view, this)
    }

    fun setTemp(temp: Int) {
        findViewById<TextView>(R.id.text_temp).text = "$temp°C"
    }
}