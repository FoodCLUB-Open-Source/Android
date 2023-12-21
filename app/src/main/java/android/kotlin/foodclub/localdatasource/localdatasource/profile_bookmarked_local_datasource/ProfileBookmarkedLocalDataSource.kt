package android.kotlin.foodclub.localdatasource.localdatasource.profile_bookmarked_local_datasource

import android.kotlin.foodclub.localdatasource.room.entity.OfflineUserBookmarksModel
import kotlinx.coroutines.flow.Flow

interface ProfileBookmarkedLocalDataSource {

    suspend fun insertBookmarkedVideosData(videos: List<OfflineUserBookmarksModel>)
    fun getBookmarkedVideosData(id: Long): Flow<OfflineUserBookmarksModel>
    suspend fun updateBookmarkedVideosData(videosModel: OfflineUserBookmarksModel)
    fun getAllBookmarkedVideosData(): Flow<List<OfflineUserBookmarksModel>>


}