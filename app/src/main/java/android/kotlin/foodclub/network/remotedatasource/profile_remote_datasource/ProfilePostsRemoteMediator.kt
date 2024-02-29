package android.kotlin.foodclub.network.remotedatasource.profile_remote_datasource

import android.kotlin.foodclub.localdatasource.room.database.FoodCLUBDatabase
import android.kotlin.foodclub.localdatasource.room.entity.OfflineUserPostsModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class ProfilePostsRemoteMediator(
    private val foodClubDb: FoodCLUBDatabase,
    private val postsApi: ProfileRemoteDataSource
): RemoteMediator<Int, OfflineUserPostsModel>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, OfflineUserPostsModel>
    ): MediatorResult {
        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = state.anchorPosition
                    if(lastItem == null) {
                        1
                    } else {
                        (lastItem / state.config.pageSize) + 1
                    }
                }
            }
            MediatorResult.Success(endOfPaginationReached = false)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        }
//        catch (e: HttpException) {
//            MediatorResult.Error(e)
//        }
    }
}