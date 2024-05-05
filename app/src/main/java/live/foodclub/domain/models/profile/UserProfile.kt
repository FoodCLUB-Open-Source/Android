package live.foodclub.domain.models.profile

data class UserProfile(
    val username: String,
    val profilePictureUrl: String? = null,

    val totalUserLikes: Int? = 0,
    val totalUserFollowers: Int? = 0,
    val totalUserFollowing: Int? = 0,
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
