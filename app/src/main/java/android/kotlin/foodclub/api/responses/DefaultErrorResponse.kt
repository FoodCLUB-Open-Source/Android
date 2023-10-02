package android.kotlin.foodclub.api.responses

import android.kotlin.foodclub.data.models.SignUpError

data class DefaultErrorResponse(
    val message: String = "",
    val errors: List<SignUpError> = listOf()
)