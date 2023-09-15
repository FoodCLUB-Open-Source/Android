package android.kotlin.foodclub.api.responses

import android.kotlin.foodclub.data.models.PostModel

data class GetPostResponse(
    val data: List<PostModel> = listOf()
)
