package live.foodclub.network.retrofit.responses.posts

import live.foodclub.network.retrofit.dtoModels.posts.PostModelDto
import androidx.annotation.Keep
import live.foodclub.network.retrofit.responses.profile.RetrievePostsListResponse

@Keep
data class GetDiscoverPostsResponse(
    val posts: List<PostModelDto> = listOf()
)

fun GetDiscoverPostsResponse.toRetrievePostsListResponse(): RetrievePostsListResponse {
    return RetrievePostsListResponse(data = posts)
}
