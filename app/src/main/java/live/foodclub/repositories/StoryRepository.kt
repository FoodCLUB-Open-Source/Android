package live.foodclub.repositories

import live.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import live.foodclub.domain.models.home.VideoModel
import live.foodclub.network.retrofit.services.StoriesService
import live.foodclub.network.retrofit.dtoMappers.stories.StoryMapper
import live.foodclub.network.retrofit.responses.stories.RetrievePostImageStoryResponse
import live.foodclub.network.retrofit.responses.stories.RetrieveUserFriendsStoriesResponse
import live.foodclub.network.retrofit.responses.stories.RetrieveUserViewedStoryResponse
import live.foodclub.network.retrofit.utils.apiRequestFlow
import live.foodclub.utils.helpers.Resource
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class StoryRepository(
    private val api: StoriesService,
    private val storyMapper: StoryMapper
) {

    suspend fun getUserFriendsStories(): Resource<List<VideoModel>, DefaultErrorResponse> {
        return when(
            val resource = apiRequestFlow<RetrieveUserFriendsStoriesResponse, DefaultErrorResponse> {
                api.getUserFriendStories()
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


    suspend fun userViewsStory(storyId: Long): Resource<RetrieveUserViewedStoryResponse, DefaultErrorResponse> {
        try {
            val response = api.userViewsStory(storyId)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    return Resource.Success(responseBody)
                }
            }
        } catch (e: Exception) {
            return Resource.Error("Failed to View Story: ${e.message}")
        }

        return Resource.Error("Failed to View Story")
    }

    suspend fun postImageStory(file: File): Resource<RetrievePostImageStoryResponse, DefaultErrorResponse> {
        return when (val resource = apiRequestFlow<RetrievePostImageStoryResponse, DefaultErrorResponse> {
            api.postImageStory(
                image = MultipartBody.Part
                    .createFormData(
                        "image",
                        file.name,
                        file.asRequestBody("image/jpg".toMediaType())
                    )
            )
        }) {
            is Resource.Success -> {
                Resource.Success(resource.data!!.body()!!)
            }
            is Resource.Error -> {
                Resource.Error(resource.message!!)
            }
        }
    }
}