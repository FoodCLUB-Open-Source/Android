package android.kotlin.foodclub.room.repository.datasource_impl

import android.kotlin.foodclub.room.db.ProfileDao
import android.kotlin.foodclub.room.entity.ProfileModel
import android.kotlin.foodclub.room.repository.datasource.ProfileDataLocalSource
import kotlinx.coroutines.flow.Flow

class ProfileDataLocalSourceImpl(private val profileDao: ProfileDao): ProfileDataLocalSource {

    override suspend fun insertData(profileModel: ProfileModel) {
        return profileDao.insertProfileData(profileModel)
    }

    override fun getData(id: Long): Flow<ProfileModel> {
        return profileDao.getProfileData(id)
    }

    override suspend fun updateData(profileModel: ProfileModel) {
        return profileDao.updateProfileData(profileModel)
    }

}