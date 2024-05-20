package live.foodclub.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import live.foodclub.domain.enums.PostType
import live.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import live.foodclub.domain.models.home.VideoModel
import live.foodclub.localdatasource.room.dao.PostDao
import live.foodclub.localdatasource.room.relationships.PostWithUser
import live.foodclub.network.remotedatasource.posts.PostsRemoteMediator
import live.foodclub.network.remotedatasource.posts.provider.PostsRemoteDataSourceProvider
import live.foodclub.network.retrofit.services.PostsService
import live.foodclub.network.retrofit.dtoMappers.posts.PostToVideoMapper
import live.foodclub.network.retrofit.responses.posts.DeletePostResponse
import live.foodclub.network.retrofit.responses.posts.GetPostResponse
import live.foodclub.network.retrofit.responses.posts.ViewsPostResponse
import live.foodclub.network.retrofit.responses.profile.RetrievePostsListResponse
import live.foodclub.network.retrofit.utils.apiRequestFlow
import live.foodclub.utils.helpers.Resource

class PostRepository(
    private val api: PostsService,
    private val postDao: PostDao,
    private val postsRemoteDataSourceProvider: PostsRemoteDataSourceProvider,
    private val postToVideoMapper: PostToVideoMapper
) {

    suspend fun getPost(id: Long): Resource<VideoModel, DefaultErrorResponse> {
        return when(
            val resource = apiRequestFlow<GetPostResponse, DefaultErrorResponse> {
                api.getPost(id)
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

    @OptIn(ExperimentalPagingApi::class)
    fun getHomePosts(): Pager<Int, PostWithUser> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = PostsRemoteMediator(
                userId = 0,
                postType = PostType.HOME,
                postDao = postDao,
                postsDataSource = postsRemoteDataSourceProvider.getHomeDataSource(),
            ),
            pagingSourceFactory = { postDao.getPagingData(PostType.HOME) }
        )
    }

    suspend fun getHomepagePosts(
        pageSize: Int? = null, pageNo: Int? = null
    ): Resource<List<VideoModel>, DefaultErrorResponse> {
        return when(
            val resource = apiRequestFlow<RetrievePostsListResponse, DefaultErrorResponse> {
                api.getHomepagePosts(pageSize, pageNo)
            }
        ) {
            is Resource.Success -> {
                Resource.Success(
                    resource.data!!.body()!!.data.map {
                        postToVideoMapper.mapToDomainModel(it)
                    }
                )
            }

            is Resource.Error -> {
                Resource.Error(resource.message!!)
            }
        }
    }

    //TODO: Pass Category and parse it to String (use one function for both world and normal categories)
    suspend fun getWorldCategoryPosts(
        userId: Long, pageSize: Int? = null, pageNo: Int? = null
    ): Resource<List<VideoModel>, DefaultErrorResponse> {
        return when(
            val resource = apiRequestFlow<RetrievePostsListResponse, DefaultErrorResponse> {
                api.getPostByWorldCategory("", pageSize, pageNo)
            }
        ) {
            is Resource.Success -> {
                Resource.Success(
                    resource.data!!.body()!!.data.map {
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
                postDao.deletePost(id)

                Resource.Success(
                    resource.data!!.body()?.status == "Post Deleted"
                )
            }

            is Resource.Error -> {
                Resource.Error(resource.message!!)
            }
        }
    }

    suspend fun userViewsPost(postId: Long): Resource<ViewsPostResponse, DefaultErrorResponse> {
        try {
            val response = api.viewsPost(postId)
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