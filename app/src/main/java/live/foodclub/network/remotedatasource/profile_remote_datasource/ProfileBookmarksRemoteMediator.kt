package live.foodclub.network.remotedatasource.profile_remote_datasource

import live.foodclub.localdatasource.room.database.FoodCLUBDatabase
import live.foodclub.localdatasource.room.entity.ProfileBookmarksEntity
import live.foodclub.network.retrofit.dtoModels.posts.PostModelDto
import live.foodclub.network.retrofit.dtoModels.posts.toProfileBookmarksEntity
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
class ProfileBookmarksRemoteMediator(
    private val userId: Long,
    private val foodClubDb: FoodCLUBDatabase,
    private val repository: ProfileRepository,
): RemoteMediator<Int, ProfileBookmarksEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ProfileBookmarksEntity>
    ): MediatorResult {
        val dao = foodClubDb.getUserProfileBookmarksDao()
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

            var userBookmarks: List<PostModelDto> = emptyList()
            when(
                val result = repository.retrieveBookmarkedPosts(
                    userId = userId,
                    pageNo = loadKey,
                    pageSize = state.config.pageSize)
            ) {
                is Resource.Error -> throw RemoteDataRetrievalException(result.message)
                is Resource.Success -> userBookmarks = result.data!!
            }

            foodClubDb.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    dao.clearAllForBookmarkedBy(userId)
                }
                val userPostsEntities = userBookmarks.map { it.toProfileBookmarksEntity(userId) }
                dao.insertBookmarkedVideosData(userPostsEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = userBookmarks.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: RemoteDataRetrievalException) {
            MediatorResult.Error(e)
        }
    }
}