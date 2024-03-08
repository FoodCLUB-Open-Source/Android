package android.kotlin.foodclub.network.remotedatasource.profile_remote_datasource

import android.kotlin.foodclub.localdatasource.room.database.FoodCLUBDatabase
import android.kotlin.foodclub.localdatasource.room.entity.OfflineUserPostsModel
import android.kotlin.foodclub.network.retrofit.dtoMappers.profile.UserLocalPostsMapper
import android.kotlin.foodclub.network.retrofit.dtoModels.posts.PostModelDto
import android.kotlin.foodclub.repositories.ProfileRepository
import android.kotlin.foodclub.utils.exceptions.RemoteDataRetrievalException
import android.kotlin.foodclub.utils.helpers.Resource
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class ProfilePostsRemoteMediator(
    private val foodClubDb: FoodCLUBDatabase,
    private val repository: ProfileRepository,
    private val localVideosMapper: UserLocalPostsMapper
): RemoteMediator<Int, OfflineUserPostsModel>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, OfflineUserPostsModel>
    ): MediatorResult {
        val dao = foodClubDb.getUserProfilePostsDao()
        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if(lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            var userPosts: List<PostModelDto> = emptyList()
            when(
                val result = repository.retrieveProfileData(
                pageNo = loadKey,
                pageSize = state.config.pageSize)
            ) {
                is Resource.Error -> throw RemoteDataRetrievalException(result.message)
                is Resource.Success -> userPosts = result.data!!.userPosts
            }

            foodClubDb.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    dao.clearAll()
                }
                val userPostsEntities = localVideosMapper.mapToDomainModel(userPosts)
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