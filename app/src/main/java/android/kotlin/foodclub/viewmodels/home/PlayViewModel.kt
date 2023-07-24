package android.kotlin.foodclub.viewmodels.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlayViewModel : ViewModel() {
    private val _title = MutableLiveData("PlayViewModel View")
    val title: LiveData<String> get() = _title
}
