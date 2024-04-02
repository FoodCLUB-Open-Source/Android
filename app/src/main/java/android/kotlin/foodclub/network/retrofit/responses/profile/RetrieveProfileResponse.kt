package android.kotlin.foodclub.network.retrofit.responses.profile

import android.kotlin.foodclub.network.retrofit.dtoModels.profile.UserProfileDto
import androidx.annotation.Keep

@Keep
data class RetrieveProfileResponse(
    val data: UserProfileDto
)
