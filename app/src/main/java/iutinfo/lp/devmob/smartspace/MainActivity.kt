package iutinfo.lp.devmob.smartspace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View

class MainActivity : AppCompatActivity() {

    private var handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        findViewById<TemperatureView>(R.id.temperatueAmbiante).setTemp(24)

        findViewById<LightView>(R.id.etatLumiere).setLight(true)
        findViewById<HeaderView>(R.id.headerView).setName("Antoine")
           //refreshData();
        //refreshDataLoop();
    }

    /*private fun refreshDataLoop() {
        handler.postDelayed({
            refreshData()
            refreshDataLoop()
        }, resources.getInteger(R.integer.refresh_time).toLong());
    }

    private fun refreshData() {
        var temp = 24 // get from API
        findViewById<TemperatureView>(R.id.etatLumiere).setTemp(temp)

        findViewById<View>(R.id.spinner).visibility = View.GONE;
    }*/

}