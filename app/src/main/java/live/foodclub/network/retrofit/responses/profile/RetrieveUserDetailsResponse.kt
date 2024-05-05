package live.foodclub.network.retrofit.responses.profile

import live.foodclub.network.retrofit.dtoModels.profile.UserDetailsDto
import androidx.annotation.Keep

@Keep
data class RetrieveUserDetailsResponse(
    val data: UserDetailsDto
)
