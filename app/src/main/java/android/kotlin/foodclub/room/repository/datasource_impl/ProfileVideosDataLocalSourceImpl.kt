package android.kotlin.foodclub.room.repository.datasource_impl

import android.kotlin.foodclub.room.dao.UserProfileVideosDao
import android.kotlin.foodclub.room.entity.OfflineProfileVideosModel
import android.kotlin.foodclub.room.repository.datasource.ProfileVideosDataLocalSource
import kotlinx.coroutines.flow.Flow

class ProfileVideosDataLocalSourceImpl(
    private val userProfileVideosDao: UserProfileVideosDao
): ProfileVideosDataLocalSource  {
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