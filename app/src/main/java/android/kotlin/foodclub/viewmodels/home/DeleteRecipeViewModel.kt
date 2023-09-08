package android.kotlin.foodclub.viewmodels.home

import android.kotlin.foodclub.data.models.VideoModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodclub.viewmodels.home.HomeViewModel

class DeleteRecipeViewModel: ViewModel() {
    private val _title = MutableLiveData("HomeViewModel View")
    val title: LiveData<String> get() = _title

    object RecipesVideos {
        val recipe_vid1 = VideoModel(
            videoId = "kylie_vid1",
            authorDetails = "kylieJenner",
            videoLink = "recipeVid.mp4",
            videoStats = VideoModel.VideoStats(
                like = 409876,
                comment = 8356,
                share = 3000,
                favourite = 1500
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
        )
        val recipesVideosList = listOf(
            recipe_vid1,
        )
    }

    val deleteVideoExemple = arrayListOf<VideoModel>().apply {
        addAll(RecipesVideos.recipesVideosList)
    }
}