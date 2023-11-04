package android.kotlin.foodclub.viewModels.home

import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.domain.models.home.VideoStats
import android.kotlin.foodclub.repositories.PostRepository
import android.kotlin.foodclub.utils.helpers.Resource
import android.kotlin.foodclub.network.retrofit.utils.SessionCache
import android.kotlin.foodclub.repositories.BookmarkRepository
import android.kotlin.foodclub.repositories.LikesRepository
import android.kotlin.foodclub.repositories.StoryRepository
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val storyRepository: StoryRepository,
    private val likesRepository: LikesRepository,
    private val bookmarkRepository: BookmarkRepository,
    private val sessionCache: SessionCache
) : ViewModel() {
    private val _title = MutableLiveData("HomeViewModel View")
    val title: LiveData<String> get() = _title

    private val _postListData = MutableStateFlow<List<VideoModel>>(listOf())
    val postListData: StateFlow<List<VideoModel>> get() = _postListData

    private val _storyListData = MutableStateFlow<List<VideoModel>>(listOf())
    val storyListData: StateFlow<List<VideoModel>> get() = _storyListData

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> get() = _error

    init {
        getPostListData()
        getUserFollowerStories()
    }

    private fun getPostListData() {
        val user = sessionCache.getActiveSession()?.sessionUser ?: return
        viewModelScope.launch {
            when(val resource = postRepository.getHomepagePosts(user.userId)) {
                is Resource.Success -> {
                    _error.value = ""
                    _postListData.value = resource.data!!
//                    setTestData()
                }
                is Resource.Error -> {
                    _error.value = resource.message!!
                }
            }
        }
    }

    private fun setTestData() {
        if(_postListData.value.isEmpty()) return
        _postListData.value = _postListData.value.map {
            VideoModel(it.videoId, it.authorDetails, it.videoStats,
                "https://kretu.sts3.pl/foodclub_videos/daniel_vid2.mp4",
                it.currentViewerInteraction, it.description, it.createdAt,
                "https://kretu.sts3.pl/foodclub_thumbnails/daniel_vid2-thumbnail.jpg")
        }
    }

    private fun getUserFollowerStories(){
        if(sessionCache.getActiveSession()?.sessionUser == null) return
        viewModelScope.launch {
            when(val resource = storyRepository.getUserFriendsStories(sessionCache.getActiveSession()!!.sessionUser.userId)) {
                is Resource.Success -> {
                    _error.value = ""
                    val originalList = resource.data
                    if (originalList?.size == 1) {
                        // ***** for testing purposes only *****
                        // If the original list has only one item,
                        // duplicate it five times with different IDs
                        val duplicatedList = mutableListOf<VideoModel>()
                        val originalItem = originalList[0]

                        for (i in 1..5) {
                            val duplicatedItem = originalItem.copy(
                                videoId = Random.nextLong()
                            )
                            duplicatedList.add(duplicatedItem)
                        }

                        _storyListData.value = duplicatedList
                    } else {
                        _storyListData.value = originalList!!
                    }

                    Log.i("MYTAG", "stories value: ${_storyListData.value}")
                }
                is Resource.Error -> {
                    _error.value = resource.message!!
                }
            }
        }
    }

    suspend fun updatePostLikeStatus(postId: Long, isLiked: Boolean){
        val userId = sessionCache.getActiveSession()?.sessionUser?.userId
        viewModelScope.launch {
            when(
                val resource = likesRepository.updatePostLikeStatus(
                    postId = postId,
                    userId = userId!!,
                    isLiked
                )
            ){
                is Resource.Success -> {
                    Log.i("MYTAG","${resource.data}")
                }
                is Resource.Error -> {
                    Log.i("MYTAG","${resource.message}")
                }
            }
        }
        updatePostById(postId, isLiked)
    }

    private fun updatePostById(postId: Long, isLiked: Boolean) {
        val currentList = _postListData.value.toMutableList() // Convert to a mutable list

        for (videoModel in currentList) {
            if (videoModel.videoId == postId) {
                videoModel.currentViewerInteraction.isLiked = isLiked
                break // Exit the loop after finding and updating the specific item
            }
        }

        _postListData.value = currentList // Update with the modified list
    }

    suspend fun updatePostBookmarkStatus(postId: Long, isBookmarked: Boolean){
        val userId = sessionCache.getActiveSession()?.sessionUser?.userId
        viewModelScope.launch {
            when(
                val resource = bookmarkRepository.updateBookmarkStatus(
                    postId = postId,
                    userId = userId!!,
                    isBookmarked
                )
            ){
                is Resource.Success -> {
                    Log.i("MYTAG","success: ${resource.data}")
                }
                is Resource.Error -> {
                    Log.i("MYTAG","error: ${resource.message}")
                }
            }
        }
        updateBookmarkStatus(postId, isBookmarked)
    }

    private fun updateBookmarkStatus(postId: Long, isBookmarked: Boolean) {
        val currentList = _postListData.value.toMutableList() // Convert to a mutable list

        for (videoModel in currentList) {
            if (videoModel.videoId == postId) {
                videoModel.currentViewerInteraction.isBookmarked = isBookmarked
                break // Exit the loop after finding and updating the specific item
            }
        }

        _postListData.value = currentList // Update with the modified list
    }

    object RecipesVideos {
        val recipe_vid1 = VideoModel(
            videoId = 1,
            authorDetails = "kylieJenner",
            videoLink = "https://kretu.sts3.pl/foodclub_videos/recipeVid.mp4",
            videoStats = VideoStats(
                like = 409876,
                comment = 8356,
                share = 3000,
                favourite = 1500
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            thumbnailLink = "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
        )
        val recipe_vid2 = VideoModel(
            videoId = 2,
            authorDetails = "kylieJenner",
            videoLink = "https://kretu.sts3.pl/foodclub_videos/daniel_vid2.mp4",
            videoStats = VideoStats(
                like = 564572,
                comment = 8790,
                share = 2000,
                favourite = 1546
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            thumbnailLink = "https://kretu.sts3.pl/foodclub_thumbnails/daniel_vid2-thumbnail.jpg"
        )
        val recipe_vid3 = VideoModel(
            videoId = 3,
            authorDetails = "kylieJenner",
            videoLink = "https://kretu.sts3.pl/foodclub_videos/recipeVid.mp4",
            videoStats = VideoStats(
                like = 2415164,
                comment = 5145,
                share = 5000,
                favourite = 2000
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            thumbnailLink = "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
        )
        val recipe_vid4 = VideoModel(
            videoId = 4,
            authorDetails = "kylieJenner",
            videoLink = "https://kretu.sts3.pl/foodclub_videos/recipeVid.mp4",
            videoStats = VideoStats(
                like = 51626,
                comment = 1434,
                share = 167,
                favourite = 633
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            thumbnailLink = "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
        )
        val recipe_vid5 = VideoModel(
            videoId = 5,
            authorDetails = "kylieJenner",
            videoLink = "https://kretu.sts3.pl/foodclub_videos/recipeVid.mp4",
            videoStats = VideoStats(
                like = 547819,
                comment = 79131,
                share = 8921,
                favourite = 2901
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            thumbnailLink = "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
        )
        val recipe_vid6 = VideoModel(
            videoId = 6,
            authorDetails = "kylieJenner",
            videoLink = "https://kretu.sts3.pl/foodclub_videos/recipeVid.mp4",
            videoStats = VideoStats(
                like = 4512340,
                comment = 65901,
                share = 8165,
                favourite = 154
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            thumbnailLink = "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
        )

        val recipe_vid7 = VideoModel(
            videoId = 7,
            authorDetails = "kylieJenner",
            videoLink = "https://kretu.sts3.pl/foodclub_videos/recipeVid.mp4",
            videoStats = VideoStats(
                like = 612907,
                comment = 7643,
                share = 1291,
                favourite = 890
            ),
            description = "Draft video testing  #foryou #fyp #compose #tik",
            thumbnailLink = "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
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
