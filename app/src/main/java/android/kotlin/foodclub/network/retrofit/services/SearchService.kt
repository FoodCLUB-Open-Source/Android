package android.kotlin.foodclub.network.retrofit.services

import android.kotlin.foodclub.network.retrofit.dtoModels.search.SearchDto
import android.kotlin.foodclub.network.retrofit.responses.search.SearchUserPostsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP

interface SearchService {
    @GET("posts/search/user-posts")
    suspend fun searchPosts(
        @Body searchText: SearchDto
    ) : Response<SearchUserPostsResponse>
}