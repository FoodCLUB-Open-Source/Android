package android.kotlin.foodclub.network.retrofit.dtoModels.profile

import android.kotlin.foodclub.network.retrofit.dtoModels.posts.PostModelDto
import com.google.gson.annotations.SerializedName

data class UserProfileDto(
    val userInfo: UserInfoDto,

    @SerializedName("total_user_likes")
    val totalUserLikes: Int,

    @SerializedName("total_user_followers")
    val totalUserFollowers: Int,

    @SerializedName("total_user_following")
    val totalUserFollowing: Int,

    @SerializedName("user_posts")
    val userPosts: List<PostModelDto> = listOf(),

    @SerializedName("top_creators")
    val topCreators: List<TopCreatorsDto> = listOf()
)
