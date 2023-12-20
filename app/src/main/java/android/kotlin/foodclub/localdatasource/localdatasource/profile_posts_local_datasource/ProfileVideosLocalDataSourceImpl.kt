package android.kotlin.foodclub.localdatasource.localdatasource.profile_posts_local_datasource

import android.kotlin.foodclub.localdatasource.room.dao.UserProfilePostsDao
import android.kotlin.foodclub.localdatasource.room.entity.OfflineUserPostsModel
import kotlinx.coroutines.flow.Flow

class ProfileVideosLocalDataSourceImpl(
    private val userProfilePostsDao: UserProfilePostsDao
): ProfilePostsLocalDataSource {
    override suspend fun insertProfileVideosData(videos: List<OfflineUserPostsModel>) {
        userProfilePostsDao.insertProfileVideosData(videos)
    }

    override fun getProfileVideosData(id: Long): Flow<OfflineUserPostsModel> {
        return userProfilePostsDao.getProfileVideosData(id)
    }

    override suspend fun updateProfileVideosData(videosModel: OfflineUserPostsModel) {
        return userProfilePostsDao.updateProfileVideosData(videosModel)
    }

    override fun getAllProfileVideosData(): Flow<List<OfflineUserPostsModel>> {
        return userProfilePostsDao.getAllProfileVideosData()
    }
}