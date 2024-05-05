package live.foodclub.network.retrofit.responses.search

import live.foodclub.network.retrofit.dtoModels.search.SearchPostsDto
import live.foodclub.network.retrofit.dtoModels.search.SearchUsersDto
import androidx.annotation.Keep

@Keep
data class SearchUserPostsResponse (
    val users: List<SearchUsersDto>,
    val posts: List<SearchPostsDto>
)