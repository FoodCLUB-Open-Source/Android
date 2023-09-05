package android.kotlin.foodclub.data.models

import com.google.gson.annotations.SerializedName

data class SignUpError(
    val type: String,
    val value: String,
    @SerializedName("msg")
    val message: String,
    val path: String,
    val location: String
)
