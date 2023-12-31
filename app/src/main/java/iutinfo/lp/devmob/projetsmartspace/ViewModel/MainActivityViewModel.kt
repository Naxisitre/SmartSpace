package iutinfo.lp.devmob.smartspace

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import iutinfo.lp.devmob.projetsmartspace.API.Data
import iutinfo.lp.devmob.projetsmartspace.API.GetDataService
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {
    val myResponse: MutableLiveData<Data?> = MutableLiveData()
    var error : Boolean = false
    private var errorTime : Int = 0
    fun getData() {
        viewModelScope.launch(){
            try {
                myResponse.value = GetDataService.retrofit.getData()
                error = false
                errorTime = 0
            } catch (e: Exception) {
                errorTime++
                error = true
                Log.i("ErrorTime", errorTime.toString())
            }

        }
    }
}