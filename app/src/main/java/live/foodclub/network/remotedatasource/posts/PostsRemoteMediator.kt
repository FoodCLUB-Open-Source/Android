package live.foodclub.network.remotedatasource.posts

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import kotlinx.coroutines.flow.firstOrNull
import live.foodclub.domain.enums.PostType
import live.foodclub.localdatasource.room.dao.PostDao
import live.foodclub.localdatasource.room.relationships.PostWithUser
import live.foodclub.network.remotedatasource.posts.sources.PostsRemoteDataSource
import live.foodclub.network.retrofit.dtoModels.posts.PostModelDto
import live.foodclub.network.retrofit.dtoModels.posts.toPostWithUser
import live.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import live.foodclub.network.retrofit.responses.profile.RetrievePostsListResponse
import live.foodclub.network.retrofit.utils.apiRequestFlow
import live.foodclub.utils.exceptions.RemoteDataRetrievalException
import live.foodclub.utils.helpers.Resource
import java.io.IOException
import kotlin.math.ceil

@OptIn(ExperimentalPagingApi::class)
class PostsRemoteMediator(
    private val userId: Long,
    private val postType: PostType,
    private val postDao: PostDao,
    private val postsDataSource: PostsRemoteDataSource
): RemoteMediator<Int, PostWithUser>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PostWithUser>,
    ): MediatorResult {
        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = postDao.countRows(postType).firstOrNull()
                    if(lastItem == null) {
                        1
                    } else {
                        ceil(lastItem.toDouble() / state.config.pageSize).toInt() + 1
                    }
                }
            }
            var userPosts: List<PostModelDto> = emptyList()
            when(
                val resource = apiRequestFlow<RetrievePostsListResponse, DefaultErrorResponse> {
                    postsDataSource.getPosts(
                        userId = userId,
                        pageNo = loadKey,
                        pageSize = state.config.pageSize
                    )
                }
            ) {
                is Resource.Success -> { userPosts = resource.data!!.body()!!.data }
                is Resource.Error -> { throw RemoteDataRetrievalException(resource.message) }
            }

            val postsWithUser = userPosts.map { it.toPostWithUser() }
            postDao.insertPaginatedDataForType(
                posts = postsWithUser,
                postType = postType,
                loadType = loadType
            )

            MediatorResult.Success(
                endOfPaginationReached = userPosts.size < state.config.pageSize
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: RemoteDataRetrievalException) {
            MediatorResult.Error(e)
        }
    }
}