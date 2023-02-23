package iutinfo.lp.devmob.smartspace

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {
    val myResponse: MutableLiveData<Data> = MutableLiveData()

    fun getData() {
        viewModelScope.launch(){
            myResponse.value = DataNetwork.retrofit.getData()
        }
    }
}