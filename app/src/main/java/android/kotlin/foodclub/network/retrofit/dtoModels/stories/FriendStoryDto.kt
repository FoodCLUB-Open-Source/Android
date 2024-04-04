package android.kotlin.foodclub.network.retrofit.dtoModels.stories

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class FriendStoryDto(
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("profile_picture")
    val profilePicture: String,
    val username: String,
    val stories: List<StoryDetailDto>
)
