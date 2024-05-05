package live.foodclub.domain.models.profile

data class SimpleUserModel(
    val userId: Int,
    val username: String,
    val profilePictureUrl: String?,
    val userFullname: String? = null
)
