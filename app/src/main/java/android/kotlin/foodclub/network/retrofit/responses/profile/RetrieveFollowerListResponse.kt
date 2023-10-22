package android.kotlin.foodclub.network.retrofit.responses.profile

import android.kotlin.foodclub.network.retrofit.dtoModels.profile.FollowerUserDto

data class RetrieveFollowerListResponse(
    val data: List<FollowerUserDto> = listOf()
)
