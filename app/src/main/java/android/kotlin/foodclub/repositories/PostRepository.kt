package android.kotlin.foodclub.repositories

import android.kotlin.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.network.retrofit.services.PostsService
import android.kotlin.foodclub.network.retrofit.dtoMappers.posts.PostToVideoMapper
import android.kotlin.foodclub.network.retrofit.responses.posts.DeletePostResponse
import android.kotlin.foodclub.network.retrofit.responses.posts.GetHomepagePostsResponse
import android.kotlin.foodclub.network.retrofit.responses.posts.GetPostResponse
import android.kotlin.foodclub.network.retrofit.responses.posts.ViewsPostResponse
import android.kotlin.foodclub.network.retrofit.utils.apiRequestFlow
import android.kotlin.foodclub.utils.helpers.Resource
import android.util.Log

class PostRepository(
    private val api: PostsService,
    private val postToVideoMapper: PostToVideoMapper
) {

    suspend fun getPost(id: Long, userId: Long): Resource<VideoModel, DefaultErrorResponse> {
        return when(
            val resource = apiRequestFlow<GetPostResponse, DefaultErrorResponse> {
                api.getPost(id, userId)
            }
        ) {
            is Resource.Success -> {
                Log.d("PostRepository", "getPost")
                Resource.Success(
                    postToVideoMapper.mapToDomainModel(resource.data!!.body()!!.data[0])
                )
            }

            is Resource.Error -> {
                Log.d("PostRepository", "getPostError")
                Resource.Error(resource.message!!)
            }
        }
    }

    suspend fun getHomepagePosts(
        userId: Long, pageSize: Int? = null, pageNo: Int? = null
    ): Resource<List<VideoModel>, DefaultErrorResponse> {
        return when(
            val resource = apiRequestFlow<GetHomepagePostsResponse, DefaultErrorResponse> {
                api.getHomepagePosts(userId, pageSize, pageNo)
            }
        ) {
            is Resource.Success -> {
                Resource.Success(
                    resource.data!!.body()!!.posts.map {
                        postToVideoMapper.mapToDomainModel(it)
                    }
                )
            }

            is Resource.Error -> {
                Resource.Error(resource.message!!)
            }
        }
    }

    suspend fun getWorldCategoryPosts(
        userId: Long, pageSize: Int? = null, pageNo: Int? = null
    ): Resource<List<VideoModel>, DefaultErrorResponse> {
        return when(
            val resource = apiRequestFlow<GetHomepagePostsResponse, DefaultErrorResponse> {
                api.getPostByWorldCategory(userId, pageSize, pageNo)
            }
        ) {
            is Resource.Success -> {
                Resource.Success(
                    resource.data!!.body()!!.posts.map {
                        postToVideoMapper.mapToDomainModel(it)
                    }
                )
            }

            is Resource.Error -> {
                Resource.Error(resource.message!!)
            }
        }
    }

    suspend fun deletePost(id: Long): Resource<Boolean, DefaultErrorResponse> {
        return when(
            val resource = apiRequestFlow<DeletePostResponse, DefaultErrorResponse> {
                api.deletePost(id)
            }
        ) {
            is Resource.Success -> {
                Resource.Success(
                    resource.data!!.body()?.status == "Post Deleted"
                )
            }

            is Resource.Error -> {
                Resource.Error(resource.message!!)
            }
        }
    }

    suspend fun userViewsPost(postId: Long, userId: Long): Resource<ViewsPostResponse, DefaultErrorResponse> {
        try {
            val response = api.viewsPost(postId, userId)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    return Resource.Success(responseBody)
                }
            }
        } catch (e: Exception) {
            return Resource.Error("Failed to View Post: ${e.message}")
        }

        return Resource.Error("Failed to View Post")
    }

}