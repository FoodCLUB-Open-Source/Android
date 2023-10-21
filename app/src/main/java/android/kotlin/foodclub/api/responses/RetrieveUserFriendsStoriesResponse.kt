package android.kotlin.foodclub.api.responses

import android.kotlin.foodclub.data.models.FriendsStory

data class RetrieveUserFriendsStoriesResponse(
    val stories: List<FriendsStory>
)
