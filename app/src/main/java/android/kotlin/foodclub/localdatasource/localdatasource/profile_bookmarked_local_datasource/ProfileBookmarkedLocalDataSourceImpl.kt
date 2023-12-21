package android.kotlin.foodclub.localdatasource.localdatasource.profile_bookmarked_local_datasource

import android.kotlin.foodclub.localdatasource.room.dao.UserProfileBookmarksDao
import android.kotlin.foodclub.localdatasource.room.entity.OfflineUserBookmarksModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileBookmarkedLocalDataSourceImpl @Inject constructor(
    private val profileBookmarksDao: UserProfileBookmarksDao
): ProfileBookmarkedLocalDataSource {
    override suspend fun insertBookmarkedVideosData(videos: List<OfflineUserBookmarksModel>) {
        profileBookmarksDao.insertBookmarkedVideosData(videos)
    }

    override fun getBookmarkedVideosData(id: Long): Flow<OfflineUserBookmarksModel> {
        return profileBookmarksDao.getBookmarkedVideosData(id)
    }

    override suspend fun updateBookmarkedVideosData(videosModel: OfflineUserBookmarksModel) {
        profileBookmarksDao.updateBookmarkedVideosData(videosModel)
    }

    override fun getAllBookmarkedVideosData(): Flow<List<OfflineUserBookmarksModel>> {
        return profileBookmarksDao.getAllBookmarkedVideosData()
    }
}