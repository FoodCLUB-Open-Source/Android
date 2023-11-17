package android.kotlin.foodclub.network.retrofit.responses.posts

import android.kotlin.foodclub.network.retrofit.dtoModels.posts.PostModelDto

data class ViewsPostResponse(
    val data: List<PostModelDto>
)
