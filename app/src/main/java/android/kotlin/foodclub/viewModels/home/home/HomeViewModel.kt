package android.kotlin.foodclub.viewModels.home.home

import android.kotlin.foodclub.domain.enums.Reactions
import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.domain.models.home.VideoStats
import android.kotlin.foodclub.domain.models.products.MyBasketCache
import android.kotlin.foodclub.domain.models.profile.SimpleUserModel
import android.kotlin.foodclub.domain.models.snaps.MemoriesModel
import android.kotlin.foodclub.domain.models.snaps.SnapModel
import android.kotlin.foodclub.network.retrofit.utils.SessionCache
import android.kotlin.foodclub.repositories.BookmarkRepository
import android.kotlin.foodclub.repositories.LikesRepository
import android.kotlin.foodclub.repositories.PostRepository
import android.kotlin.foodclub.repositories.RecipeRepository
import android.kotlin.foodclub.repositories.StoryRepository
import android.kotlin.foodclub.views.home.home.feed.HomeState
import android.kotlin.foodclub.utils.helpers.Resource
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val storyRepository: StoryRepository,
    private val likesRepository: LikesRepository,
    private val bookmarkRepository: BookmarkRepository,
    private val recipeRepository: RecipeRepository,
    private val basketCache: MyBasketCache,
    val exoPlayer: ExoPlayer
) : ViewModel(), HomeEvents {

    companion object {
        private val TAG = HomeViewModel::class.java.simpleName
    }

    private val _state = MutableStateFlow(HomeState.default())
    val state: StateFlow<HomeState>
        get() = _state

    init {
        exoPlayer.prepare()
        getPostListData()
        getUserFollowerStories()
        getMemoriesListData()
    }

    override fun postSnap(file: File) {
        viewModelScope.launch {
            when (val resource = storyRepository.postImageStory(file)) {
                is Resource.Success -> {
                    Log.i(TAG, "POST STORY ${resource.data}")
                }

                is Resource.Error -> {
                    Log.e(TAG, "POST STORY ${resource.message}")
                }
            }
        }
    }

    override suspend fun userViewsStory(storyId: Long) {
        viewModelScope.launch {
            when (val resource = storyRepository.userViewsStory(storyId)) {
                is Resource.Success -> {
                    Log.i(TAG, "User Viewed the Story Successfully")
                }

                is Resource.Error -> {
                    Log.e(TAG, "Failed to View Story: ${resource.message}")
                }
            }
        }
    }

    override suspend fun userViewsPost(postId: Long) {
        viewModelScope.launch {
            when (val resource = postRepository.userViewsPost(postId)) {
                is Resource.Success -> {
                    Log.i(TAG, "Viewed Post Successfully")
                }

                is Resource.Error -> {
                    Log.e(TAG, "Failed to View Post: ${resource.message}")
                }
            }
        }
    }

    override suspend fun updatePostLikeStatus(postId: Long, isLiked: Boolean) {
        viewModelScope.launch {
            when (
                val resource = likesRepository.updatePostLikeStatus(
                    postId = postId,
                    isLiked
                )
            ) {
                is Resource.Success -> {
                    Log.i(TAG, "${resource.data}")
                }

                is Resource.Error -> {
                    Log.i(TAG, "${resource.message}")
                }
            }
        }
        updatePostById(postId, isLiked)
    }
    override fun getRecipe(recipeId: Long) {
        viewModelScope.launch {
            when (val resource = recipeRepository.getRecipe(recipeId)) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            recipe = resource.data
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update { it.copy(error = resource.message!!) }
                }
            }
        }
    }

    override fun addIngredientsToBasket() {
        val basket = basketCache.getBasket()
        val selectedIngredients = _state.value.recipe?.ingredients?.filter { it.isSelected }
        selectedIngredients?.forEach {
            it.isSelected = false
            basket.addIngredient(it.copy())
        }
        basketCache.saveBasket(basket)
    }
    override fun updatePostBookmarkStatus(postId: Long, isBookmarked: Boolean) {
        viewModelScope.launch {
            when (
                val resource = bookmarkRepository.updateBookmarkStatus(
                    postId = postId,
                    isBookmarked
                )
            ) {
                is Resource.Success -> {
                    Log.i(TAG, "success: ${resource.data}")
                }

                is Resource.Error -> {
                    Log.i(TAG, "error: ${resource.message}")
                }
            }
        }
        updateBookmarkStatus(postId, isBookmarked)
    }

    override fun toggleShowMemories(show: Boolean) {
        _state.update { it.copy(showMemories = show) }
    }

    override fun toggleShowMemoriesReel(show: Boolean) {
        _state.update { it.copy(showMemoriesReel = show) }
    }

    override fun selectReaction(reaction: Reactions) {
        _state.update { it.copy(selectedReaction = reaction) }
        Log.d(TAG, "selected reaction: ${state.value.selectedReaction}")
    }

    private fun getMemoriesListData() {
        val list = mutableListOf(
            MemoriesModel(
                stories = listOf(
                    SnapModel(
                        snapAuthor = SimpleUserModel(
                            123,
                            "Shivendu Mishra",
                            "https://kretu.sts3.pl/foodclub_thumbnails/daniel_vid2-thumbnail.jpg"
                        ),
                        userReactions = mapOf(
                            SimpleUserModel(
                                567,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.YUMMY,
                            SimpleUserModel(
                                5632,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.CREATIVE,
                            SimpleUserModel(
                                56527,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.YUMMY,
                            SimpleUserModel(
                                5637,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.LETSGOTOGETHER,
                            SimpleUserModel(
                                5627,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.LETSGOTOGETHER,
                            SimpleUserModel(
                                562347,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.YUMMY,
                            SimpleUserModel(
                                56867,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.LETSGOTOGETHER,
                            SimpleUserModel(
                                568747,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.LETSGOTOGETHER,
                            SimpleUserModel(
                                5623547,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.YUMMY,
                            SimpleUserModel(
                                56577,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.STAYHEALTHY,
                            SimpleUserModel(
                                56897,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.STAYHEALTHY,
                            SimpleUserModel(
                                5678695,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.STAYHEALTHY,
                            SimpleUserModel(
                                56757,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.STAYHEALTHY
                        ),
                        isSaved = false,
                        imageUrl = "https://kretu.sts3.pl/foodclub_thumbnails/daniel_vid2-thumbnail.jpg",
                        dateTime = "21 November 2023"
                    ),
                    SnapModel(
                        snapAuthor = SimpleUserModel(
                            123,
                            "Shivendu ",
                            "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                        ),
                        userReactions = mapOf(
                            SimpleUserModel(
                                435,
                                "Orhan",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.CREATIVE
                        ),
                        isSaved = false,
                        imageUrl = "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg",
                        dateTime = "22 November 2023"
                    ),
                ),
                dateTime = "20 November 2023"
            ),
            MemoriesModel(
                stories = listOf(
                    SnapModel(
                        snapAuthor = SimpleUserModel(
                            123,
                            "Shivendu Mishra",
                            "https://kretu.sts3.pl/foodclub_thumbnails/daniel_vid2-thumbnail.jpg"
                        ),
                        userReactions = mapOf(
                            SimpleUserModel(
                                567,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.YUMMY,
                            SimpleUserModel(
                                5632,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.CREATIVE,
                            SimpleUserModel(
                                56527,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.YUMMY,
                            SimpleUserModel(
                                5637,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.LETSGOTOGETHER,
                            SimpleUserModel(
                                5627,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.LETSGOTOGETHER,
                            SimpleUserModel(
                                562347,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.YUMMY,
                            SimpleUserModel(
                                56867,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.LETSGOTOGETHER,
                            SimpleUserModel(
                                568747,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.LETSGOTOGETHER,
                            SimpleUserModel(
                                5623547,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.YUMMY,
                            SimpleUserModel(
                                56577,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.STAYHEALTHY,
                            SimpleUserModel(
                                56897,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.STAYHEALTHY,
                            SimpleUserModel(
                                5678695,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.STAYHEALTHY,
                            SimpleUserModel(
                                56757,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.STAYHEALTHY
                        ),
                        isSaved = false,
                        imageUrl = "https://kretu.sts3.pl/foodclub_thumbnails/daniel_vid2-thumbnail.jpg",
                        dateTime = "21 November 2023"
                    ),
                    SnapModel(
                        snapAuthor = SimpleUserModel(
                            123,
                            "Shivendu ",
                            "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                        ),
                        userReactions = mapOf(
                            SimpleUserModel(
                                435,
                                "Orhan",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.CREATIVE
                        ),
                        isSaved = false,
                        imageUrl = "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg",
                        dateTime = "22 November 2023"
                    ),
                ),
                dateTime = "21 November 2023"
            ),
            MemoriesModel(
                stories = listOf(
                    SnapModel(
                        snapAuthor = SimpleUserModel(
                            123,
                            "Shivendu Mishra",
                            "https://kretu.sts3.pl/foodclub_thumbnails/daniel_vid2-thumbnail.jpg"
                        ),
                        userReactions = mapOf(
                            SimpleUserModel(
                                567,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.YUMMY,
                            SimpleUserModel(
                                5632,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.CREATIVE,
                            SimpleUserModel(
                                56527,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.YUMMY,
                            SimpleUserModel(
                                5637,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.LETSGOTOGETHER,
                            SimpleUserModel(
                                5627,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.LETSGOTOGETHER,
                            SimpleUserModel(
                                562347,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.YUMMY,
                            SimpleUserModel(
                                56867,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.LETSGOTOGETHER,
                            SimpleUserModel(
                                568747,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.LETSGOTOGETHER,
                            SimpleUserModel(
                                5623547,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.YUMMY,
                            SimpleUserModel(
                                56577,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.STAYHEALTHY,
                            SimpleUserModel(
                                56897,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.STAYHEALTHY,
                            SimpleUserModel(
                                5678695,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.STAYHEALTHY,
                            SimpleUserModel(
                                56757,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.STAYHEALTHY
                        ),
                        isSaved = false,
                        imageUrl = "https://kretu.sts3.pl/foodclub_thumbnails/daniel_vid2-thumbnail.jpg",
                        dateTime = "21 November 2023"
                    ),
                    SnapModel(
                        snapAuthor = SimpleUserModel(
                            123,
                            "Shivendu ",
                            "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                        ),
                        userReactions = mapOf(
                            SimpleUserModel(
                                435,
                                "Orhan",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.CREATIVE
                        ),
                        isSaved = false,
                        imageUrl = "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg",
                        dateTime = "22 November 2023"
                    ),
                ),
                dateTime = "22 November 2023"
            ),
            MemoriesModel(
                stories = listOf(
                    SnapModel(
                        snapAuthor = SimpleUserModel(
                            123,
                            "Shivendu Mishra",
                            "https://kretu.sts3.pl/foodclub_thumbnails/daniel_vid2-thumbnail.jpg"
                        ),
                        userReactions = mapOf(
                            SimpleUserModel(
                                567,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.YUMMY,
                            SimpleUserModel(
                                5632,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.CREATIVE,
                            SimpleUserModel(
                                56527,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.YUMMY,
                            SimpleUserModel(
                                5637,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.LETSGOTOGETHER,
                            SimpleUserModel(
                                5627,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.LETSGOTOGETHER,
                            SimpleUserModel(
                                562347,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.YUMMY,
                            SimpleUserModel(
                                56867,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.LETSGOTOGETHER,
                            SimpleUserModel(
                                568747,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.LETSGOTOGETHER,
                            SimpleUserModel(
                                5623547,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.YUMMY,
                            SimpleUserModel(
                                56577,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.STAYHEALTHY,
                            SimpleUserModel(
                                56897,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.STAYHEALTHY,
                            SimpleUserModel(
                                5678695,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.STAYHEALTHY,
                            SimpleUserModel(
                                56757,
                                "Jakub",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.STAYHEALTHY
                        ),
                        isSaved = false,
                        imageUrl = "https://kretu.sts3.pl/foodclub_thumbnails/daniel_vid2-thumbnail.jpg",
                        dateTime = "21 November 2023"
                    ),
                    SnapModel(
                        snapAuthor = SimpleUserModel(
                            123,
                            "Shivendu ",
                            "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                        ),
                        userReactions = mapOf(
                            SimpleUserModel(
                                435,
                                "Orhan",
                                "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg"
                            ) to Reactions.CREATIVE
                        ),
                        isSaved = false,
                        imageUrl = "https://kretu.sts3.pl/foodclub_thumbnails/recipeVid-thumbnail.jpg",
                        dateTime = "22 November 2023"
                    ),
                ),
                dateTime = "23 November 2023"
            )
        )
        _state.update { it.copy(memories = list) }
    }

    private fun getPostListData() {
        viewModelScope.launch {
            when (val resource = postRepository.getHomepagePosts()) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            videoList = resource.data!!,
                            error = ""
                        )
                    }
//                    setTestData()
                }

                is Resource.Error -> {
                    _state.update { it.copy(error = resource.message!!) }
                }
            }
        }
    }

    private fun setTestData() {
        if (state.value.videoList.isEmpty()) return
        val videos = state.value.videoList.map {
            VideoModel(
                it.videoId, it.authorDetails, it.videoStats,
                "https://kretu.sts3.pl/foodclub_videos/daniel_vid2.mp4",
                it.currentViewerInteraction, it.description, it.createdAt,
                "https://kretu.sts3.pl/foodclub_thumbnails/daniel_vid2-thumbnail.jpg"
            )
        }
        _state.update { it.copy(videoList = videos) }
    }

    private fun getUserFollowerStories() {
        viewModelScope.launch {
            when (val resource =
                storyRepository.getUserFriendsStories()) {
                is Resource.Success -> {
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
                        _state.update {
                            it.copy(
                                error = "",
                                storyList = duplicatedList
                            )
                        }
                    } else {
                        _state.update {
                            it.copy(
                                error = "",
                                storyList = originalList!!
                            )
                        }
                    }

                    Log.i(TAG, "stories value: ${state.value.storyList}")
                }

                is Resource.Error -> {
                    _state.update { it.copy(error = resource.message!!) }
                }
            }
        }
    }

    private fun updatePostById(postId: Long, isLiked: Boolean) {
        val currentList = state.value.videoList.toMutableList()

        for (videoModel in currentList) {
            if (videoModel.videoId == postId) {
                videoModel.currentViewerInteraction.isLiked = isLiked
                break
            }
        }

        _state.update { it.copy(videoList = currentList) }
    }

    private fun updateBookmarkStatus(postId: Long, isBookmarked: Boolean) {
        val currentList = state.value.videoList.toMutableList()

        for (videoModel in currentList) {
            if (videoModel.videoId == postId) {
                videoModel.currentViewerInteraction.isBookmarked = isBookmarked
                break
            }
        }
        _state.update { it.copy(videoList = currentList) }
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

    override fun onCleared() {
        super.onCleared()
        exoPlayer.release()
    }
}
