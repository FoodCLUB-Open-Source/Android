package android.kotlin.foodclub.room.repository.datasource_impl

import android.kotlin.foodclub.room.dao.UserDetailsDao
import android.kotlin.foodclub.room.entity.OfflineProfileModel
import android.kotlin.foodclub.room.repository.datasource.ProfileDataLocalSource
import kotlinx.coroutines.flow.Flow

class ProfileDataLocalSourceImpl(private val profileDao: UserDetailsDao): ProfileDataLocalSource {

    override suspend fun insertData(profileModel: OfflineProfileModel) {
        return profileDao.insertProfileData(profileModel)
    }

    override fun getData(id: Long): Flow<OfflineProfileModel> {
        return profileDao.getProfileData(id)
    }

    override suspend fun updateData(profileModel: OfflineProfileModel) {
        return profileDao.updateProfileData(profileModel)
    }

}