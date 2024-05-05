package android.kotlin.foodclub.network.retrofit.services

import android.kotlin.foodclub.network.retrofit.responses.search.SearchUserPostsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SearchService {
    @GET("posts/search/user-posts/{searchText}")
    suspend fun searchPosts(
        @Path("searchText") searchText: String
    ): Response<SearchUserPostsResponse>
}