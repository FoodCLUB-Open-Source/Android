package android.kotlin.foodclub.network.retrofit.responses.profile

import android.kotlin.foodclub.network.retrofit.dtoModels.profile.UserProfileDto

data class RetrieveProfileResponse(
    val data: UserProfileDto
)
