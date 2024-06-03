package live.foodclub.domain.models.profile

import live.foodclub.domain.models.auth.FirebaseUserModel

data class SimpleUserModel(
    val userId: Long,
    val username: String,
    val profilePictureUrl: String?,
    val userFullName: String? = null,
) {
    companion object {
        fun default(): SimpleUserModel {
            return SimpleUserModel(
                userId = 0,
                username = "",
                profilePictureUrl = "",
                userFullName = ""
            )
        }
    }

    fun mapToFirebaseUserModel(): FirebaseUserModel{
        return FirebaseUserModel(
            userID = userId.toInt(),
            username = username,
            profileImageUrl = profilePictureUrl ?: "",
            fullName = userFullName ?: "",
        )
    }
}
