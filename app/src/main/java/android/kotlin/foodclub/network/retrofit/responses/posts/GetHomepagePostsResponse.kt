package android.kotlin.foodclub.network.retrofit.responses.posts

import android.kotlin.foodclub.network.retrofit.dtoModels.posts.PostModelDto

data class GetHomepagePostsResponse(
    val posts: List<PostModelDto> = listOf()
)
