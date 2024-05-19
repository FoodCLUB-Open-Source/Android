package live.foodclub.network.retrofit.dtoModels.profile

import live.foodclub.network.retrofit.dtoModels.posts.PostModelDto
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import live.foodclub.network.retrofit.responses.profile.RetrievePostsListResponse

@Keep
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

fun UserProfileDto.toRetrievePostsListResponse(): RetrievePostsListResponse {
    return RetrievePostsListResponse(data = userPosts)
}
