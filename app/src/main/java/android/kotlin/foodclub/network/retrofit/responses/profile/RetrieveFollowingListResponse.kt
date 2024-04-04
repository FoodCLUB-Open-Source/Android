package android.kotlin.foodclub.network.retrofit.responses.profile

import android.kotlin.foodclub.network.retrofit.dtoModels.profile.FollowingUserDto
import androidx.annotation.Keep

@Keep
data class RetrieveFollowingListResponse(
    val data: List<FollowingUserDto> = listOf()
)
