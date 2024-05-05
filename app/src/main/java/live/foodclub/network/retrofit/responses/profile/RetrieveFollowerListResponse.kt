package live.foodclub.network.retrofit.responses.profile

import live.foodclub.network.retrofit.dtoModels.profile.FollowerUserDto
import androidx.annotation.Keep

@Keep
data class RetrieveFollowerListResponse(
    val data: List<FollowerUserDto> = listOf()
)
