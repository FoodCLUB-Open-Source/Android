package android.kotlin.foodclub.viewmodels.home

import android.kotlin.foodclub.data.models.VideoModel
import android.kotlin.foodclub.repositories.PostRepository
import android.kotlin.foodclub.utils.helpers.Resource
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DeleteRecipeViewModel @AssistedInject constructor(
    private val repository: PostRepository,
    @Assisted private val postId: Long
): ViewModel() {
    private val _title = MutableLiveData("HomeViewModel View")
    val title: LiveData<String> get() = _title

    private val _postData = MutableStateFlow<VideoModel?>(null)
    val postData: StateFlow<VideoModel?> get() = _postData

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> get() = _error

    init {
        getPostData()
    }

    private fun getPostData() {
        viewModelScope.launch {
            when(val resource = repository.getPost(postId)) {
                is Resource.Success -> {
                    _error.value = ""
                    _postData.value = resource.data
                     setTestData(_postData.value!!.videoLink, _postData.value!!.thumbnailLink)
                }
                is Resource.Error -> {
                    _error.value = resource.message!!
                }
            }
        }
    }

    private fun setTestData(videoLink: String, thumbnailLink: String) {
        if(_postData.value == null) return
        val post = _postData.value!!
        _postData.value = VideoModel(post.videoId, post.authorDetails, post.videoStats,
            videoLink,
            post.currentViewerInteraction, post.description, post.createdAt,
            thumbnailLink)
    }

    fun deleteCurrentPost() {
        viewModelScope.launch {
            Log.d("DeleteRecipeViewModel", "postId: $postId")
            when(val resource = repository.deletePost(postId)) {
                is Resource.Success -> {
                    _error.value = ""
                    Log.d("DeleteRecipeViewModel", "success")
                    _postData.value = null
                }
                is Resource.Error -> {
                    _error.value = resource.message!!
                    Log.d("DeleteRecipeViewModel", "error: ${_error.value}")
                }
            }
        }
    }

    object RecipesVideos {
        val recipe_vid1 = VideoModel(
            videoId = 1,
            authorDetails = "kylieJenner",
            videoLink = "asset:///recipeVid.mp4",
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

    @AssistedFactory
    interface Factory {
        fun create(postId: Long): DeleteRecipeViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory, postId: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(postId) as T
            }
        }
    }
}