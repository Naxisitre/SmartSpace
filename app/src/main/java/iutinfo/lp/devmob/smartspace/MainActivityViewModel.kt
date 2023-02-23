package iutinfo.lp.devmob.smartspace

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {
    val myResponse: MutableLiveData<Data?> = MutableLiveData()
    var error : Boolean = true
    var errorTime : Int = 0
    fun getData() {
        viewModelScope.launch(){
            try {
                myResponse.value = DataNetwork.retrofit.getData()
                error = false
            } catch (e: Exception) {
                error = true
            }

        }
    }
}