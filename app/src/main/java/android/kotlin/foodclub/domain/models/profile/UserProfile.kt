package android.kotlin.foodclub.domain.models.profile

data class UserProfile(
    val username: String,
    val profilePictureUrl: String? = null,

    val totalUserLikes: Int,
    val totalUserFollowers: Int,
    val totalUserFollowing: Int,

    val userPosts: List<UserPosts> = listOf(),
    val topCreators: List<SimpleUserModel> = listOf()
)
