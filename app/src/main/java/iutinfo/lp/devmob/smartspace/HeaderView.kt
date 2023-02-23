package iutinfo.lp.devmob.smartspace

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView

class HeaderView(context: Context, attrs : AttributeSet?) : FrameLayout(context, attrs) {
    init {
        inflate(context, R.layout.header_view, this)
    }
     //fun setName(name: String) {
      //   findViewById<TextView>(R.id.hello_text).text =
        //     resources.getString(R.string.hello_user, name)
     //}
}