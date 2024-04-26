package live.foodclub.network.retrofit.responses.profile

import live.foodclub.network.retrofit.dtoModels.profile.FollowingUserDto
import androidx.annotation.Keep

@Keep
data class RetrieveFollowingListResponse(
    val data: List<FollowingUserDto> = listOf()
)
