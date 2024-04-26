package live.foodclub.network.retrofit.dtoModels.search

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SearchUsersDto (
    @SerializedName("id")
    val userId: Int,

    val username: String,

    @SerializedName("full_name")
    val userFullname: String?,

    @SerializedName("profile_picture")
    val profilePictureUrl: String?
)