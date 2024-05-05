package live.foodclub.network.retrofit.responses.profile

import live.foodclub.network.retrofit.dtoModels.posts.PostModelDto
import androidx.annotation.Keep

@Keep
data class RetrievePostsListResponse(
    val data: List<PostModelDto> = listOf()
)
