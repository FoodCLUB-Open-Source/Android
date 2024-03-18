package android.kotlin.foodclub.localdatasource.localdatasource.profile_local_datasource

import android.kotlin.foodclub.localdatasource.room.dao.ProfileDataDao
import android.kotlin.foodclub.localdatasource.room.entity.ProfileEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileLocalDataSourceImpl @Inject constructor(private val profileDao: ProfileDataDao):
    ProfileLocalDataSource {
    override suspend fun insertProfileLocalData(profileEntity: ProfileEntity) {
        profileDao.insertProfileData(profileEntity)
    }

    override fun getProfileData(userId: Long): Flow<ProfileEntity> {
        return profileDao.getProfileData(userId)
    }

    override fun clearProfileData() {
        profileDao.clearProfileData()
    }
}