package com.example.foodclub.viewmodels.home

import android.kotlin.foodclub.data.models.VideoModel
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class HomeViewModel : ViewModel() {
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
        val recipe_vid2 = VideoModel(
            videoId = "kylie_vid2",
            authorDetails = "kylieJenner",
            videoLink = "daniel_vid2.mp4",
            videoStats = VideoModel.VideoStats(
                like = 564572,
                comment = 8790,
                share = 2000,
                favourite = 1546
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
        )
        val recipe_vid3 = VideoModel(
            videoId = "kylie_vid3",
            authorDetails = "kylieJenner",
            videoLink = "recipeVid.mp4",
            videoStats = VideoModel.VideoStats(
                like = 2415164,
                comment = 5145,
                share = 5000,
                favourite = 2000
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
        )
        val recipe_vid4 = VideoModel(
            videoId = "kylie_vid4",
            authorDetails = "kylieJenner",
            videoLink = "recipeVid.mp4",
            videoStats = VideoModel.VideoStats(
                like = 51626,
                comment = 1434,
                share = 167,
                favourite = 633
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
        )
        val recipe_vid5 = VideoModel(
            videoId = "kylie_vid5",
            authorDetails = "kylieJenner",
            videoLink = "recipeVid.mp4",
            videoStats = VideoModel.VideoStats(
                like = 547819,
                comment = 79131,
                share = 8921,
                favourite = 2901
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
        )
        val recipe_vid6 = VideoModel(
            videoId = "kylie_vid6",
            authorDetails = "kylieJenner",
            videoLink = "recipeVid.mp4",
            videoStats = VideoModel.VideoStats(
                like = 4512340,
                comment = 65901,
                share = 8165,
                favourite = 154
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
        )

        val recipe_vid7 = VideoModel(
            videoId = "kylie_vid7",
            authorDetails = "kylieJenner",
            videoLink = "recipeVid.mp4",
            videoStats = VideoModel.VideoStats(
                like = 612907,
                comment = 7643,
                share = 1291,
                favourite = 890
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
        )

        val recipesVideosList = listOf(
            recipe_vid1,
            recipe_vid2,
            recipe_vid3,
            recipe_vid4,
            recipe_vid5,
            recipe_vid6,
            recipe_vid7
        )
    }
    val videosList = arrayListOf<VideoModel>().apply {
        addAll(RecipesVideos.recipesVideosList)
    }
}
