package android.kotlin.foodclub.repositories

import android.kotlin.foodclub.api.authentication.API
import android.kotlin.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import android.kotlin.foodclub.data.models.FriendsStory
import android.kotlin.foodclub.data.models.VideoModel
import android.kotlin.foodclub.utils.helpers.Resource
import android.util.Log
import kotlinx.coroutines.delay
import kotlin.random.Random

class StoryRepository(
    private val api: API
) {
    private fun mapDtoToModel(dtoModel: FriendsStory): VideoModel {
        return VideoModel(
            videoId = Random.nextLong(),// use random for now because json data is not long causing NumberFormatException
            authorDetails = dtoModel.username,
            videoStats = VideoModel.VideoStats(
                15,
                0L,
                0L,
                0L,
                10
            ),
            videoLink = dtoModel.stories[0].videoUrl, // use 0 index for now, later pass model list and loop it
            description = "Friends' Story",
            thumbnailLink = dtoModel.stories[0].thumbnailUrl
        )
    }

    suspend fun getUserFriendsStories(id: Long): Resource<List<VideoModel>, DefaultErrorResponse> {
        try {
            val response = api.getUserFriendStories(id)
            delay(500)
            if (response.isSuccessful && response.body()?.stories != null) {
                val responseBody = response.body()
                if (responseBody != null) {
                    val stories = responseBody.stories
                    val storyList = stories.map { story ->
                        mapDtoToModel(story)
                    }
                    Log.i("MYTAG", "$storyList")

                    return Resource.Success(storyList)
                } else {
                    Log.i("MYTAG", "response body null")
                }
            } else {
                Log.i("MYTAG", "REQUEST NOT SUCCESSFUL")
            }
        } catch (e: Exception) {
            Log.i("MYTAG", "Error: ${e.message}", e)
            return Resource.Error("Error: ${e.message}", null)
        }
        return Resource.Error("unexpected error", null)
    }

}