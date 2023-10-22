package android.kotlin.foodclub.network.retrofit.responses.general

import android.kotlin.foodclub.network.retrofit.dtoModels.other.ErrorDto

data class DefaultErrorResponse(
    val message: String?,
    val errors: List<ErrorDto> = listOf()
)