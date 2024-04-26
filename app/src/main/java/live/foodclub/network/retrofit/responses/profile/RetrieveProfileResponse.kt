package live.foodclub.network.retrofit.responses.profile

import live.foodclub.network.retrofit.dtoModels.profile.UserProfileDto
import androidx.annotation.Keep

@Keep
data class RetrieveProfileResponse(
    val data: UserProfileDto
)
