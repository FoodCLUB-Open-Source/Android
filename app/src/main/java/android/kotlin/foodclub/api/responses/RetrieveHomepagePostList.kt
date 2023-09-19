package android.kotlin.foodclub.api.responses

import android.kotlin.foodclub.data.models.PostModel

data class RetrieveHomepagePostList(
    val posts: List<PostModel> = listOf()
)
