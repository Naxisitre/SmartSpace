package iutinfo.lp.devmob.projetsmartspace


import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File

class ProblemeActivity() : AppCompatActivity() {
    var uri : Uri? = null
    val takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) { boolean: Boolean? ->
        Log.i("Picture", "here is uri" + uri)
        if(boolean!!) {
            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri)
            findViewById<ImageView>(R.id.retourPhoto).setImageBitmap(bitmap)
            findViewById<ImageView>(R.id.retourPhoto).visibility = VISIBLE
            findViewById<TextView>(R.id.text_photo).visibility = GONE
            findViewById<ImageView>(R.id.viewImage).visibility = GONE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.photo_view)
        supportActionBar?.hide()
    }

    fun activateCamera(view: View) {
        val photoFile = File.createTempFile(
            "IMG_",
            ".jpg",
            getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        )

        uri = FileProvider.getUriForFile(
            this,
            "${packageName}.provider",
            photoFile
        )
        takePicture.launch(uri)
    }
}