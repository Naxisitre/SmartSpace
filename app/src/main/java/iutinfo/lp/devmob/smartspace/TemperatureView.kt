package iutinfo.lp.devmob.smartspace

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView


class TemperatureView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    init {
        inflate(context, R.layout.temperature_view, this)
    }

    fun setTemp(temp: Int?) {
        if (temp == null){
            findViewById<TextView>(R.id.text_temp).text = "--°C"
        }
        findViewById<TextView>(R.id.text_temp).text = "$temp°C"
    }
}