package android.kotlin.foodclub.network.retrofit.dtoModels.profile

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UserDetailsDto(
    val id: Long,
    @SerializedName("username")
    val userName: String,
    val email: String,
    @SerializedName("phone_number")
    val phoneNumber: String? = null,
    @SerializedName("profile_picture")
    val profilePicture: String? = null,
    @SerializedName("user_bio")
    val userBio: String? = null,
    val gender: String? = null,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("date_of_birth")
    val dateOfBirth: String? = null,
    @SerializedName("dietary_preferences")
    val dietaryPrefs: List<String>? = null,
    @SerializedName("country")
    val country: String? = null,
    @SerializedName("shipping_address")
    val shippingAddress: String? = null,
    @SerializedName("full_name")
    val fullName: String? = null
)
