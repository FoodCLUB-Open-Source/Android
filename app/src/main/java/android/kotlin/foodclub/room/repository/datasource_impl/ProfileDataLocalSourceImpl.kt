package android.kotlin.foodclub.room.repository.datasource_impl

import android.kotlin.foodclub.domain.models.profile.UserDetailsModel
import android.kotlin.foodclub.room.dao.UserDetailsDao
import android.kotlin.foodclub.room.entity.OfflineProfileModel
import android.kotlin.foodclub.room.repository.datasource.ProfileDataLocalSource
import kotlinx.coroutines.flow.Flow

class ProfileDataLocalSourceImpl(private val profileDao: UserDetailsDao): ProfileDataLocalSource {

    override suspend fun insertProfile(profileModel: UserDetailsModel) {
        return profileDao.insertProfileData(profileModel)
    }

    override fun getProfile(id: Long): Flow<UserDetailsModel> {
        return profileDao.getProfileData(id)
    }

    override suspend fun updateProfile(profileModel: UserDetailsModel) {
        return profileDao.updateProfileData(profileModel)
    }

    override fun deleteProfile(profileModel: UserDetailsModel) {
        return profileDao.deleteProfileData(profileModel)
    }

    override fun clearProfiles() {
        return profileDao.clearProfileData()
    }

}