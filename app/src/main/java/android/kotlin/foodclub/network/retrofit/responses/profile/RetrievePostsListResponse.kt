package android.kotlin.foodclub.network.retrofit.responses.profile

import android.kotlin.foodclub.network.retrofit.dtoModels.profile.UserPostsDto

data class RetrievePostsListResponse(
    val data: List<UserPostsDto> = listOf()
)
