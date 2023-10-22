package android.kotlin.foodclub.network.retrofit.dtoModels.profile

import com.google.gson.annotations.SerializedName

data class UserProfileDto(
    val username: String,

    @SerializedName("profile_picture")
    val profilePictureUrl: String? = null,

    @SerializedName("total_user_likes")
    val totalUserLikes: Int,

    @SerializedName("total_user_followers")
    val totalUserFollowers: Int,

    @SerializedName("total_user_following")
    val totalUserFollowing: Int,

    @SerializedName("user_posts")
    val userPosts: List<UserPostsDto> = listOf(),

    @SerializedName("top_creators")
    val topCreators: List<TopCreatorsDto> = listOf()
)
