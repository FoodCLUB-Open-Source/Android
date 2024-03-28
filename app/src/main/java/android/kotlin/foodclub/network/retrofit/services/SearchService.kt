package android.kotlin.foodclub.network.retrofit.services

import android.kotlin.foodclub.network.retrofit.responses.search.SearchUserPostsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("posts/search/user-posts")
    suspend fun searchPosts(
        @Query("search_text") searchText: String
    ): Response<SearchUserPostsResponse>
}