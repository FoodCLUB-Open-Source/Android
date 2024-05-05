package live.foodclub.repositories

import live.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import live.foodclub.network.retrofit.responses.likes.PostLikesStatusResponse
import live.foodclub.network.retrofit.services.LikesService
import live.foodclub.network.retrofit.utils.apiRequestFlow
import live.foodclub.utils.helpers.Resource

class LikesRepository(val api: LikesService) {

    suspend fun updatePostLikeStatus(
        postId: Long,
        isLiked: Boolean
    ): Resource<PostLikesStatusResponse, DefaultErrorResponse> {
        val resource = when (isLiked) {
            true -> {
                apiRequestFlow<PostLikesStatusResponse, DefaultErrorResponse> {
                    api.updatePostLikeStatus(postId = postId)
                }
            }
            false -> {
                apiRequestFlow<PostLikesStatusResponse, DefaultErrorResponse> {
                    api.deletePostLikeStatus(postId = postId)
                }
            }
        }

        return when (resource) {
            is Resource.Success -> {
                Resource.Success(resource.data!!.body()!!)
            }
            is Resource.Error -> {
                Resource.Error(resource.message!!.toString())
            }
        }
    }
}