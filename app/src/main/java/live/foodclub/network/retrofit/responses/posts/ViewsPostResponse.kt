package live.foodclub.network.retrofit.responses.posts

import live.foodclub.network.retrofit.dtoModels.posts.PostModelDto
import androidx.annotation.Keep

@Keep
data class ViewsPostResponse(
    val data: List<PostModelDto>
)
