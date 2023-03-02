package iutinfo.lp.devmob.smartspace.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import iutinfo.lp.devmob.smartspace.API.GetDataService
import iutinfo.lp.devmob.smartspace.API.IDUsers
import iutinfo.lp.devmob.smartspace.API.UserInfo
import kotlinx.coroutines.launch

class AuthentificationActivityViewModel: ViewModel() {
    private val myResponse: MutableLiveData<UserInfo?> = MutableLiveData()
    val myResponseList: MutableLiveData<List<IDUsers?>> = MutableLiveData()
    fun postData(userId: String) {
        viewModelScope.launch {
            try {
                Log.i("Authentification", "postData: $userId")
                myResponse.value = GetDataService.retrofit.addUser(userId)
                //myResponse.value = PostDataService.retrofit.testLogin(userId)
            }
            catch (e: Exception) {
            }
            }
        }

    fun getIDUsers() {
        viewModelScope.launch {
            try {
                myResponseList.value = GetDataService.retrofit.getAllID()
            }
            catch (e: Exception) {
            }
            }
        }
    }