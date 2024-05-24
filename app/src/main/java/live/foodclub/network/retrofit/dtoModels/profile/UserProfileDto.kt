package live.foodclub.network.retrofit.dtoModels.profile

import android.util.Log
import live.foodclub.network.retrofit.dtoModels.posts.PostModelDto
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import live.foodclub.localdatasource.room.entity.ProfileEntity
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
    val posts = userPosts.map { it.copy(user = userInfo) }
    return RetrievePostsListResponse(data = posts)
}

fun UserProfileDto.toProfileEntity(): ProfileEntity {
    return ProfileEntity(
        userId = userInfo.id,
        userName = userInfo.username,
        fullName = userInfo.fullName ?: "Undefined",
        profilePicture = userInfo.profilePictureUrl,
        totalUserFollowers = totalUserFollowers,
        totalUserFollowing = totalUserFollowing,
        totalUserLikes = totalUserLikes
    )
}