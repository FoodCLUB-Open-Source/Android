package live.foodclub.network.retrofit.dtoModels.profile

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import live.foodclub.localdatasource.room.entity.ProfileEntity

@Keep
data class UserInfoDto(
    val id: Long,
    val username: String,
    @SerializedName("profile_picture")
    val profilePictureUrl: String? = null,
    @SerializedName("full_name")
    val fullName: String? = null
)

fun UserInfoDto.toProfileEntity(): ProfileEntity {
    return ProfileEntity(
        userId = id,
        userName = username,
        fullName = fullName ?: "Undefined",
        profilePicture = profilePictureUrl
    )
}
