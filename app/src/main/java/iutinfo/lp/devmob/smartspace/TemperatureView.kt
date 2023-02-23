package iutinfo.lp.devmob.smartspace

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import java.time.LocalDateTime


class TemperatureView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    init {
        inflate(context, R.layout.temperature_view, this)
        findViewById<ImageView>(R.id.attention).visibility = GONE
    }

    fun setTemp(temp: Int?, created_at: String) {
        if (temp == null){
            findViewById<TextView>(R.id.text_temp).text = "--°C"
            findViewById<ImageView>(R.id.attention).visibility = VISIBLE
            findViewById<TextView>(R.id.refresh_text).text = resources.getString(R.string.refresh_warning_text)

        }
        else {
            findViewById<TextView>(R.id.text_temp).text = "$temp°C"
            findViewById<TextView>(R.id.refresh_text).text =
                resources.getString(R.string.refresh_text, "$created_at")
            findViewById<ImageView>(R.id.attention).visibility = GONE
        }
    }
}