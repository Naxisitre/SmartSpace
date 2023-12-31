package iutinfo.lp.devmob.projetsmartspace


import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import iutinfo.lp.devmob.projetsmartspace.ViewModel.ProblemActivityViewModel
import java.io.File

class ProblemeActivity() : AppCompatActivity() {

    val takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) { boolean: Boolean? ->
        Log.i("Picture", "here is uri" + viewModel.uri)
        if(boolean!!) {
            viewModel.bitmap= MediaStore.Images.Media.getBitmap(this.getContentResolver(), viewModel.uri)
            Log.i("Image", "here is bitmap" + viewModel.bitmap)
            findViewById<ImageView>(R.id.retourPhoto).setImageBitmap(viewModel.bitmap)
            findViewById<ImageView>(R.id.retourPhoto).visibility = VISIBLE
            findViewById<TextView>(R.id.text_photo).visibility = GONE
            findViewById<ImageView>(R.id.viewImage).visibility = GONE
        }
    }
 lateinit var viewModel: ProblemActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.photo_view)
        supportActionBar?.hide()
        viewModel = ViewModelProvider(this).get(ProblemActivityViewModel::class.java)
        val intent = getIntent()
        viewModel.userId = intent.getStringExtra("userID")
    }

    fun activateCamera(view: View) {
        viewModel.photoFile = File.createTempFile(
            "IMG_",
            ".jpg",
            getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        )
        Log.i("File", viewModel.photoFile.toString())
        viewModel.uri = FileProvider.getUriForFile(
            this,
            "${packageName}.provider",
            viewModel.photoFile as File
        )
        takePicture.launch(viewModel.uri)
    }

    fun EnvoieProbleme(view: View) {
        viewModel.textDesc = findViewById<EditText>(R.id.desc_prob_text).text.toString()
        viewModel.titre = findViewById<EditText>(R.id.desc_title_text).text.toString()
        //multipartImage = MultipartBody.Part.createFormData("image", File(uri!!.path!!).name, requestFile)
        try {
            if(viewModel.uri != null && viewModel.textDesc != "" && viewModel.titre != "") {
                Log.i("Uri", viewModel.uri.toString())
                Log.i("Text", viewModel.textDesc!!)
                val dialog = AlertDialog.Builder(this).setView(R.layout.popupnfc_view).show()
                dialog.findViewById<TextView>(R.id.title)?.text= "Problème envoyé"
                dialog.findViewById<TextView>(R.id.text_nfc)!!.visibility = GONE
                dialog.findViewById<TextView>(R.id.active_nfc)!!.visibility = GONE
                //viewModel.postProblem(uri, userID, textDesc)

                //Log.i("Multipart", multipartImage.toString())
                Log.i("userID", viewModel.userId.toString())
                Log.i("desc", viewModel.textDesc!!)
                //viewModel.postProblem(multipartImage, RequestBody.create(MediaType.parse("text/plain"), userID!!), RequestBody.create(MediaType.parse("text/plain"), textDesc))
                viewModel.postProblem()
                dialog.setOnDismissListener{
                    val intentProbleme = Intent(this, MainActivity::class.java)
                    startActivity(intentProbleme)
                }
                FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w(ContentValues.TAG, "Fetching FCM registration token failed", task.exception)
                        return@OnCompleteListener
                    }
                    // Get new FCM registration token
                    viewModel.token = task.result
                    //Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()

                    // Post
                    //viewModel.postNotif(token, "test")

                })
            }
            else if(viewModel.uri == null && viewModel.textDesc != "" && viewModel.titre != ""){
                val dialog = AlertDialog.Builder(this).setView(R.layout.popupnfc_view).show()
                dialog.findViewById<TextView>(R.id.title)?.text= "Veuillez prendre une photo"
                dialog.findViewById<TextView>(R.id.text_nfc)!!.visibility = GONE
                dialog.findViewById<TextView>(R.id.active_nfc)!!.visibility = GONE
            }
            else if(viewModel.uri != null && viewModel.textDesc == "" && viewModel.titre != ""){
                val dialog = AlertDialog.Builder(this).setView(R.layout.popupnfc_view).show()
                dialog.findViewById<TextView>(R.id.title)?.text= "Veuillez décrire le problème"
                dialog.findViewById<TextView>(R.id.text_nfc)!!.visibility = GONE
                dialog.findViewById<TextView>(R.id.active_nfc)!!.visibility = GONE
            }
            else if(viewModel.uri != null && viewModel.textDesc != "" && viewModel.titre == ""){
                val dialog = AlertDialog.Builder(this).setView(R.layout.popupnfc_view).show()
                dialog.findViewById<TextView>(R.id.title)?.text= "Veuillez donner un titre au problème"
                dialog.findViewById<TextView>(R.id.text_nfc)!!.visibility = GONE
                dialog.findViewById<TextView>(R.id.active_nfc)!!.visibility = GONE
            }
            else{
                val dialog = AlertDialog.Builder(this).setView(R.layout.popupnfc_view).show()
                dialog.findViewById<TextView>(R.id.title)?.text= "Veuillez remplir tous les champs"
                dialog.findViewById<TextView>(R.id.text_nfc)!!.visibility = GONE
                dialog.findViewById<TextView>(R.id.active_nfc)!!.visibility = GONE
            }
        } catch (e: Exception) {
            Log.i("Error", e.toString())
        }
    }
}