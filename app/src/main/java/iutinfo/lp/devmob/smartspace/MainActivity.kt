package iutinfo.lp.devmob.smartspace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        findViewById<TemperatureView>(R.id.temperatueAmbiante).setTemp(24)
        findViewById<TemperatureView>(R.id.temperatueAmbiante)
    }

}