package android.kotlin.foodclub.network.retrofit.dtoModels.other

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ErrorDto(
    val type: String,
    val value: String,
    @SerializedName("msg")
    val message: String,
    val path: String,
    val location: String
)

