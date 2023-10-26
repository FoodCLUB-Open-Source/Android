package android.kotlin.foodclub.network.retrofit.responses.stories

import android.kotlin.foodclub.network.retrofit.dtoModels.stories.FriendStoryDto

data class RetrieveUserFriendsStoriesResponse(
    val stories: List<FriendStoryDto>
)
