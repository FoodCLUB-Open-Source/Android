package live.foodclub.domain.models.profile

data class SimpleUserModel(
    val userId: Long,
    val username: String,
    val profilePictureUrl: String?,
    val userFullName: String? = null
)
