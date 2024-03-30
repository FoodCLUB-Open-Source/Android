package android.kotlin.foodclub.network.retrofit.responses.general

import android.kotlin.foodclub.network.retrofit.dtoModels.other.ErrorDto
import androidx.annotation.Keep

@Keep
data class DefaultErrorResponse(
    val message: String?,
    val errors: List<ErrorDto> = listOf()
)