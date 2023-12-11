package android.kotlin.foodclub.network.retrofit.responses.profile

import android.kotlin.foodclub.network.retrofit.dtoModels.posts.PostModelDto

data class RetrievePostsListResponse(
    val data: List<PostModelDto> = listOf()
)
