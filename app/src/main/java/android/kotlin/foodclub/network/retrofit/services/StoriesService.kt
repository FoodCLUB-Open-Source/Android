package android.kotlin.foodclub.network.retrofit.services

import android.kotlin.foodclub.network.retrofit.responses.stories.RetrieveUserFriendsStoriesResponse
import android.kotlin.foodclub.network.retrofit.responses.stories.RetrieveUserViewedStoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface StoriesService {
    @GET("stories/{userId}/following_stories")
    suspend fun getUserFriendStories(
        @Path("userId") userId: Long,
    ): Response<RetrieveUserFriendsStoriesResponse>

    // USER VIEWS A STORY
    @POST("likes_views/story/{storyId}/view/{userId}")
    suspend fun userViewsStory(
        @Path("storyId") storyId: String,
        @Path("userId") userId: Long
    ): Response<RetrieveUserViewedStoryResponse>
}