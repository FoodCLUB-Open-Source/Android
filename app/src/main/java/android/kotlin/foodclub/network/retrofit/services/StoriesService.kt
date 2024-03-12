package android.kotlin.foodclub.network.retrofit.services

import android.kotlin.foodclub.network.retrofit.responses.stories.RetrievePostImageStoryResponse
import android.kotlin.foodclub.network.retrofit.responses.stories.RetrieveUserFriendsStoriesResponse
import android.kotlin.foodclub.network.retrofit.responses.stories.RetrieveUserViewedStoryResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface StoriesService {
    @GET("stories/following_stories")
    suspend fun getUserFriendStories(): Response<RetrieveUserFriendsStoriesResponse>

    // USER VIEWS A STORY
    @POST("likes_views/story/{storyId}/view")
    suspend fun userViewsStory(
        @Path("storyId") storyId: Long
    ): Response<RetrieveUserViewedStoryResponse>

    @Multipart
    @POST("stories")
    suspend fun postImageStory(
        @Part image: MultipartBody.Part
    ): Response<RetrievePostImageStoryResponse>
}