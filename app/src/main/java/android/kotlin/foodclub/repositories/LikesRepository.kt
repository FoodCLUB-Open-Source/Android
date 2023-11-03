package android.kotlin.foodclub.repositories

import android.kotlin.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import android.kotlin.foodclub.network.retrofit.responses.likes.PostLikesStatusResponse
import android.kotlin.foodclub.network.retrofit.services.LikesService
import android.kotlin.foodclub.network.retrofit.utils.apiRequestFlow
import android.kotlin.foodclub.utils.helpers.Resource

class LikesRepository(val api: LikesService) {

    suspend fun updatePostLikeStatus(
        postId: Long,
        userId: Long,
        isLiked: Boolean
    ): Resource<PostLikesStatusResponse, DefaultErrorResponse> {
        val resource = when (isLiked) {
            true -> {
                apiRequestFlow<PostLikesStatusResponse, DefaultErrorResponse> {
                    api.updatePostLikeStatus(postId = postId, userId = userId)
                }
            }
            false -> {
                apiRequestFlow<PostLikesStatusResponse, DefaultErrorResponse> {
                    api.deletePostLikeStatus(postId = postId, userId = userId)
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