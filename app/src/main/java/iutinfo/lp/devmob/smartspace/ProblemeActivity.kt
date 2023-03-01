package iutinfo.lp.devmob.smartspace


import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import android.widget.ImageView
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
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.photo_view)
        supportActionBar?.hide()
        /*findViewById<LinearLayout>(R.id.photo).setOnClickListener {
            val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            filePhoto = getPhotoFile(FILE_NAME)*/


            /*val providerFile =FileProvider.getUriForFile(this,"com.example.androidcamera.fileprovider", filePhoto)
            takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerFile)
            if (takePhotoIntent.resolveActivity(this.packageManager) != null){
                startActivityForResult(takePhotoIntent, REQUEST_CODE)
            }else {
                Toast.makeText(this,"Camera could not open", Toast.LENGTH_SHORT).show()
            }
        }*/
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
    /*companion object {
        private val IMAGE_CHOOSE = 1000;
        private val PERMISSION_CODE = 1001;
    }

    private fun getPhotoFile(fileName: String): File {
        val directoryStorage = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", directoryStorage)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val takenPhoto = BitmapFactory.decodeFile(filePhoto.absolutePath)
            findViewById<ImageView>(R.id.viewImage).setImageBitmap(takenPhoto)
        }
        else {
            super.onActivityResult(requestCode, resultCode, data)
        }
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            findViewById<ImageView>(R.id.viewImage).setImageURI(data?.data)
        }
    }*/
}
//private const val REQUEST_CODE = 13
//private lateinit var filePhoto: File
//private const val FILE_NAME = "photo.jpg"