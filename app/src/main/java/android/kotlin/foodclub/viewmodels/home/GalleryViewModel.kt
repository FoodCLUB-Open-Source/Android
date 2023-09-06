package android.kotlin.foodclub.viewmodels.home

import android.kotlin.foodclub.R
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GalleryViewModel : ViewModel() {
    private val _title = MutableLiveData("GalleryViewModel View")
    val title: LiveData<String> get() = _title
    //Hard coded data for now
    val ResourceIds : List<Pair<String, String>> = arrayListOf(
        Pair(R.drawable.imagecard.toString(), "Image"),
        Pair(R.drawable.ic_launcher_foreground.toString(), "Image"),
        Pair(R.drawable.ic_launcher_foreground.toString(), "Image"),
        Pair(R.drawable.ic_launcher_foreground.toString(), "Image"),
        Pair(R.drawable.imagecard.toString(), "Image"),
    )
}
