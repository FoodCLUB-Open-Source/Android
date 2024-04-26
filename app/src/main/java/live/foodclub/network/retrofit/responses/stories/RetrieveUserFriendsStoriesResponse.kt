package live.foodclub.network.retrofit.responses.stories

import live.foodclub.network.retrofit.dtoModels.stories.FriendStoryDto
import androidx.annotation.Keep

@Keep
data class RetrieveUserFriendsStoriesResponse(
    val stories: List<FriendStoryDto>
)
