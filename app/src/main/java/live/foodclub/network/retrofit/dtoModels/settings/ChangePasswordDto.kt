package live.foodclub.network.retrofit.dtoModels.settings

import com.google.gson.annotations.SerializedName

data class ChangePasswordDto(
    @SerializedName("old_password")
    val oldPassword: String,
    @SerializedName("new_password")
    val newPassword: String,
)
