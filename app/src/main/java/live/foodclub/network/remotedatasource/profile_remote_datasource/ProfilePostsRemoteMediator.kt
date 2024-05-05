package live.foodclub.network.remotedatasource.profile_remote_datasource

import live.foodclub.localdatasource.room.database.FoodCLUBDatabase
import live.foodclub.localdatasource.room.entity.ProfilePostsEntity
import live.foodclub.network.retrofit.dtoModels.posts.PostModelDto
import live.foodclub.network.retrofit.dtoModels.posts.toProfilePostsEntity
import live.foodclub.repositories.ProfileRepository
import live.foodclub.utils.exceptions.RemoteDataRetrievalException
import live.foodclub.utils.helpers.Resource
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import kotlinx.coroutines.flow.firstOrNull
import java.io.IOException
import kotlin.math.ceil

@OptIn(ExperimentalPagingApi::class)
class ProfilePostsRemoteMediator(
    private val userId: Long,
    private val foodClubDb: FoodCLUBDatabase,
    private val repository: ProfileRepository,
): RemoteMediator<Int, ProfilePostsEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ProfilePostsEntity>
    ): MediatorResult {
        val dao = foodClubDb.getUserProfilePostsDao()

        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = dao.countRows().firstOrNull()
                    if(lastItem == null) {
                        1
                    } else {
                        ceil(lastItem.toDouble() / state.config.pageSize).toInt() + 1
                    }
                }
            }
            var userPosts: List<PostModelDto> = emptyList()
            when(
                val result = repository.retrieveProfileData(
                    userId = userId,
                    pageNo = loadKey,
                    pageSize = state.config.pageSize)
            ) {
                is Resource.Error -> throw RemoteDataRetrievalException(result.message)
                is Resource.Success -> userPosts = result.data!!.userPosts
            }

            foodClubDb.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    dao.clearAllForAuthor(userId)
                }
                val userPostsEntities = userPosts.map { it.toProfilePostsEntity(userId) }
                dao.insertProfileVideosData(userPostsEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = userPosts.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: RemoteDataRetrievalException) {
            MediatorResult.Error(e)
        }
    }
}