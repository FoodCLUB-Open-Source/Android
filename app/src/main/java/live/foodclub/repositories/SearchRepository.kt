package live.foodclub.repositories

import live.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import live.foodclub.network.retrofit.responses.search.SearchUserPostsResponse
import live.foodclub.network.retrofit.services.SearchService
import live.foodclub.utils.helpers.Resource

class SearchRepository(
    private val api: SearchService
) {
    suspend fun searchByText(
        searchText: String
    ): Resource<SearchUserPostsResponse, DefaultErrorResponse> {
        try {
            val response = api.searchPosts(searchText)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    return Resource.Success(responseBody)
                }
            }
        } catch (e: Exception) {
            return Resource.Error("Failed to search by Text: ${e.message}")
        }

        return Resource.Error("Failed to search by Text")
    }
}