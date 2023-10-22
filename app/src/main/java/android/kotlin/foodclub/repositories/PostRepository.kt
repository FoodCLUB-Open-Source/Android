package android.kotlin.foodclub.repositories

import android.kotlin.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import android.kotlin.foodclub.domain.models.home.VideoModel
import android.kotlin.foodclub.network.retrofit.apiInterfaces.PostsService
import android.kotlin.foodclub.network.retrofit.dtoMappers.posts.PostToVideoMapper
import android.kotlin.foodclub.network.retrofit.responses.posts.DeletePostResponse
import android.kotlin.foodclub.network.retrofit.responses.posts.GetHomepagePostsResponse
import android.kotlin.foodclub.network.retrofit.responses.posts.GetPostResponse
import android.kotlin.foodclub.network.retrofit.utils.apiRequestFlow
import android.kotlin.foodclub.utils.helpers.Resource

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
                Resource.Success(
                    postToVideoMapper.mapToDomainModel(resource.data!!.body()!!.data[0])
                )
            }

            is Resource.Error -> {
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

}