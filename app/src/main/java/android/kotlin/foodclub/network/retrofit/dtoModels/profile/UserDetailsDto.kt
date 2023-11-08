package android.kotlin.foodclub.network.retrofit.dtoModels.profile

import com.google.gson.annotations.SerializedName

data class UserDetailsDto(
    val id: Long,
    @SerializedName("username")
    val userName: String,
    val email: String,
    @SerializedName("phone_number")
    val phoneNumber: Long? = null,
    @SerializedName("profile_picture")
    val profilePicture: String,
    @SerializedName("user_bio")
    val userBio: String? = null,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("date_of_birth")
    val dateOfBirth: String? = null,
    @SerializedName("dietary_preferences")
    val dietaryPrefs: List<String>? = null
)
