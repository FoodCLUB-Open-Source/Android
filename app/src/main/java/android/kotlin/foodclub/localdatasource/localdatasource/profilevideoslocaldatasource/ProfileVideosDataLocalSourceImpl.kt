package android.kotlin.foodclub.localdatasource.localdatasource.profilevideoslocaldatasource

import android.kotlin.foodclub.localdatasource.room.dao.UserProfileVideosDao
import android.kotlin.foodclub.localdatasource.room.entity.OfflineProfileVideosModel
import kotlinx.coroutines.flow.Flow

class ProfileVideosDataLocalSourceImpl(
    private val userProfileVideosDao: UserProfileVideosDao
): ProfileVideosDataLocalSource {
    override suspend fun insertProfileVideosData(videosModel: OfflineProfileVideosModel) {
        userProfileVideosDao.insertProfileVideosData(videosModel)
    }

    override fun getProfileVideosData(id: Long): Flow<OfflineProfileVideosModel> {
        return userProfileVideosDao.getProfileVideosData(id)
    }

    override suspend fun updateProfileVideosData(videosModel: OfflineProfileVideosModel) {
        return userProfileVideosDao.updateProfileVideosData(videosModel)
    }

    override fun getAllProfileVideosData(): Flow<List<OfflineProfileVideosModel>> {
        return userProfileVideosDao.getAllProfileVideosData()
    }
}