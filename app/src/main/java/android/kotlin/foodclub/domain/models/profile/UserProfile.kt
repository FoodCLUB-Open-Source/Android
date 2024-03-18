package android.kotlin.foodclub.domain.models.profile

data class UserProfile(
    val username: String,
    val profilePictureUrl: String? = null,

    val totalUserLikes: Int,
    val totalUserFollowers: Int,
    val totalUserFollowing: Int,
) {
    companion object {
        fun default() = UserProfile(
            username = "Undefined",
            totalUserFollowers = 0,
            totalUserFollowing = 0,
            totalUserLikes = 0
        )
    }

}
