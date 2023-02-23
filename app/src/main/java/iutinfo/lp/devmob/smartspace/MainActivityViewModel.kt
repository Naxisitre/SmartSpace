package iutinfo.lp.devmob.smartspace

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {
    val myResponse: MutableLiveData<Data?> = MutableLiveData()
    var error : Boolean = false
    var errorTime : Int = 0
    fun getData() {
        viewModelScope.launch(){
            try {
                myResponse.value = DataNetwork.retrofit.getData()
                error = false
                errorTime = 0
            } catch (e: Exception) {
                errorTime++
                error = true

            }

        }
    }
}