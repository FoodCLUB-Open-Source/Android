package android.kotlin.foodclub.localdatasource.localdatasource.profile_posts_local_datasource

import android.kotlin.foodclub.localdatasource.room.entity.OfflineUserPostsModel
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow

interface ProfilePostsLocalDataSource {

    suspend fun insertProfileVideosData(videos: List<OfflineUserPostsModel>)
    fun getProfileVideosData(id: Long): Flow<OfflineUserPostsModel>

    fun pagingSource(): PagingSource<Int, OfflineUserPostsModel>
    suspend fun updateProfileVideosData(videosModel: OfflineUserPostsModel)
    fun getAllProfileVideosData(): Flow<List<OfflineUserPostsModel>>

}