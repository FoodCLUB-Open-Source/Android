package android.kotlin.foodclub.viewmodels.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StoryViewModel : ViewModel() {
    private val _title = MutableLiveData("Story View")
    val title: LiveData<String> get() = _title
}
