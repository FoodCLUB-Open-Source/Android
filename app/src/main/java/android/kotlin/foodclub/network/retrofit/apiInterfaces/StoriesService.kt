package android.kotlin.foodclub.network.retrofit.apiInterfaces

import android.kotlin.foodclub.network.retrofit.responses.stories.RetrieveUserFriendsStoriesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface StoriesService {
    @GET("stories/{userId}/following_stories")
    suspend fun getUserFriendStories(
        @Path("userId") userId: Long,
    ): Response<RetrieveUserFriendsStoriesResponse>
}