package android.kotlin.foodclub.localdatasource.localdatasource.userdetailslocaldatasource

import android.kotlin.foodclub.localdatasource.room.entity.UserDetailsModel
import android.kotlin.foodclub.localdatasource.room.dao.UserDetailsDao
import android.kotlin.foodclub.localdatasource.localdatasource.userdetailslocaldatasource.UserDetailsLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDetailsLocalDataSourceImpl @Inject constructor(
    private val userDetailsDao: UserDetailsDao
) : UserDetailsLocalDataSource {

    override suspend fun insertProfile(userDetails: UserDetailsModel) {
        return userDetailsDao.insertProfileData(userDetails)
    }

    override fun getProfile(id: Long): Flow<UserDetailsModel> {
        return userDetailsDao.getProfileData(id)
    }

    override suspend fun updateProfile(userDetails: UserDetailsModel) {
        return userDetailsDao.updateProfileData(userDetails)
    }

    override fun deleteProfile(userDetails: UserDetailsModel) {
        return userDetailsDao.deleteProfileData(userDetails)
    }

    override fun clearProfiles() {
        return userDetailsDao.clearProfileData()
    }

}