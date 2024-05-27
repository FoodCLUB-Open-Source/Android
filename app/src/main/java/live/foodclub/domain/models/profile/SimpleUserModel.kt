package live.foodclub.domain.models.profile

import live.foodclub.domain.models.auth.FirebaseUserModel

data class SimpleUserModel(
    val userId: Int,
    val username: String,
    val profilePictureUrl: String?,
    val userFullname: String? = null,
) {
    companion object {
        fun default(): SimpleUserModel {
            return SimpleUserModel(
                userId = 0,
                username = "",
                profilePictureUrl = "",
                userFullname = ""
            )
        }
    }

    fun mapToFirebaseUserModel(): FirebaseUserModel{
        return FirebaseUserModel(
            userID = userId,
            username = username,
            profileImageUrl = profilePictureUrl ?: "",
            fullName = userFullname ?: "",
        )
    }
}
