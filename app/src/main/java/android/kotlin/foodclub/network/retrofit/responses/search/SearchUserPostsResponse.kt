package android.kotlin.foodclub.network.retrofit.responses.search

import android.kotlin.foodclub.network.retrofit.dtoModels.search.SearchPostsDto
import android.kotlin.foodclub.network.retrofit.dtoModels.search.SearchUsersDto
import androidx.annotation.Keep

@Keep
data class SearchUserPostsResponse (
    val users: List<SearchUsersDto>,
    val posts: List<SearchPostsDto>
)