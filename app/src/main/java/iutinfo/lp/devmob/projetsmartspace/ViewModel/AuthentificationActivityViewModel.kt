package iutinfo.lp.devmob.projetsmartspace.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import iutinfo.lp.devmob.projetsmartspace.API.GetDataService
import iutinfo.lp.devmob.projetsmartspace.API.IDUsers
import iutinfo.lp.devmob.projetsmartspace.API.UserInfo
import kotlinx.coroutines.launch

class AuthentificationActivityViewModel: ViewModel() {
    private val myResponse: MutableLiveData<UserInfo?> = MutableLiveData()
    val myResponseList: MutableLiveData<List<IDUsers?>> = MutableLiveData()
    var error : Boolean = false
    fun postData(userId: String) {
        viewModelScope.launch {
            try {
                Log.i("Authentification", "postData: $userId")
                myResponse.value = GetDataService.retrofit.addUser(userId)
                //myResponse.value = PostDataService.retrofit.testLogin(userId)
            }
            catch (e: Exception) {
                error = true
            }
            }
        }

    fun getIDUsers() {

        viewModelScope.launch {
            try {
                myResponseList.value = GetDataService.retrofit.getAllID()
            }
            catch (e: Exception) {
                error = true
            }
            }
        }
    }