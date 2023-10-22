package android.kotlin.foodclub.repositories

import android.kotlin.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.network.retrofit.services.StoriesService
import android.kotlin.foodclub.network.retrofit.dtoMappers.stories.StoryMapper
import android.kotlin.foodclub.network.retrofit.responses.stories.RetrieveUserFriendsStoriesResponse
import android.kotlin.foodclub.network.retrofit.utils.apiRequestFlow
import android.kotlin.foodclub.utils.helpers.Resource

class StoryRepository(
    private val api: StoriesService,
    private val storyMapper: StoryMapper
) {

    suspend fun getUserFriendsStories(id: Long): Resource<List<VideoModel>, DefaultErrorResponse> {
        return when(
            val resource = apiRequestFlow<RetrieveUserFriendsStoriesResponse, DefaultErrorResponse> {
                api.getUserFriendStories(id)
            }
        ) {
            is Resource.Success -> {
                val returnList = ArrayList<VideoModel>()
                resource.data!!.body()!!.stories.forEach {
                    returnList.addAll(storyMapper.mapToDomainModel(it))
                }

                Resource.Success(
                     returnList
                )
            }

            is Resource.Error -> {
                Resource.Error(resource.message!!)
            }
        }
    }

}