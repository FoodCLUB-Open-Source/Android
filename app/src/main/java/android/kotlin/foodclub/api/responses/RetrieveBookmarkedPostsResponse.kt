package android.kotlin.foodclub.api.responses

import android.kotlin.foodclub.data.models.UserPostsModel

data class RetrieveBookmarkedPostsResponse(
    val data: List<UserPostsModel> = listOf()
)
