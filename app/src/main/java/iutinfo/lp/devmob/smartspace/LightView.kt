package iutinfo.lp.devmob.smartspace

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

class LightView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs){
    init {
        inflate(context, R.layout.light_view, this)
    }
    fun setLight(light: String, created_at: String) {
        if (light == "On") {
            findViewById<ImageView>(R.id.light_icon).setImageResource(R.drawable.lamp_on)
            findViewById<TextView>(R.id.title_light).text = resources.getString(R.string.light_state, "allumée")
            findViewById<TextView>(R.id.refresh_text_light).text = resources.getString(R.string.refresh_text, "$created_at")
            findViewById<ImageView>(R.id.attention_light).visibility = GONE
        }
        else if(light == "Off"){
            findViewById<ImageView>(R.id.light_icon).setImageResource(R.drawable.lamp_off)
            findViewById<TextView>(R.id.title_light).text = resources.getString(R.string.light_state, "éteinte")
            findViewById<TextView>(R.id.refresh_text_light).text = resources.getString(R.string.refresh_text, "$created_at")
            findViewById<ImageView>(R.id.attention_light).visibility = GONE
        }
        else if(light == "Error") {
            findViewById<ImageView>(R.id.attention_light).visibility = VISIBLE
        }
    }
}