package live.foodclub.viewModels.home.search

import live.foodclub.domain.models.home.VideoModel
import live.foodclub.domain.models.home.VideoStats
import live.foodclub.domain.models.profile.SimpleUserModel
import live.foodclub.repositories.SearchRepository
import live.foodclub.utils.helpers.Resource
import live.foodclub.views.home.search.SearchState
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel(), SearchEvents {

    companion object {
        private val TAG = SearchViewModel::class.java.simpleName
    }

    private val _state = MutableStateFlow(SearchState.default())

    val state: StateFlow<SearchState>
        get() = _state

    override suspend fun searchByText(searchText: String) {
        viewModelScope.launch {
            val userList: List<SimpleUserModel>?
            val postList: List<VideoModel>?

            when (
                val resource = searchRepository.searchByText(searchText)
            ) {
                is Resource.Success -> {
                    userList = if (resource.data?.users?.isNotEmpty() == true) {
                        resource.data.users.map {
                            SimpleUserModel(
                                it.userId,
                                it.username,
                                it.profilePictureUrl,
                                it.userFullname
                            )
                        }
                    } else listOf()

                    postList = if (resource.data?.posts?.isNotEmpty() == true) {
                        resource.data.posts.map {
                            VideoModel(
                                videoId = it.postId.toLong(),
                                authorDetails = SimpleUserModel(userId = 0,username = "", profilePictureUrl = null),
                                description = it.description,
                                videoLink = it.videoUrl,
                                thumbnailLink = it.thumbnailUrl,
                                createdAt = it.createdAt,
                                videoStats = VideoStats()
                            )
                        }
                    } else listOf()

                    _state.update {
                        it.copy(
                            userList = userList,
                            postList = postList
                        )
                    }
                }

                is Resource.Error -> {
                    Log.e(TAG, "Unexpected Error during searching")
                    Log.e(TAG, resource.message!!)
                }
            }
        }
    }
}