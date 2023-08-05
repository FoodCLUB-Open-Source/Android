package android.kotlin.foodclub.viewmodels.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateRecipeViewModel : ViewModel() {
    private val _title = MutableLiveData("CreateRecipeViewModel View")
    val title: LiveData<String> get() = _title
}
