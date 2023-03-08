package iutinfo.lp.devmob.projetsmartspace.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import iutinfo.lp.devmob.projetsmartspace.API.Data
import iutinfo.lp.devmob.projetsmartspace.API.GetDataService
import iutinfo.lp.devmob.projetsmartspace.API.Notification
import kotlinx.coroutines.launch

class ProblemeActivityViewModel : ViewModel() {
    val myResponse: MutableLiveData<Notification> = MutableLiveData()
    var error : Boolean = false
    var errorTime : Int = 0
    fun postNotif(token : String, message : String) {
        viewModelScope.launch() {
            try {
                var notification = Notification(token, message)
                myResponse.value = GetDataService.retrofit.sendNotification(notification)
                error = false
                errorTime = 0
            } catch (e: Exception) {
                errorTime++
                error = true
            }
        }
    }
}