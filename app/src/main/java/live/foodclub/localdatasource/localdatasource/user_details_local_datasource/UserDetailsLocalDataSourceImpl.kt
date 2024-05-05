package live.foodclub.localdatasource.localdatasource.user_details_local_datasource

import live.foodclub.localdatasource.room.entity.UserDetailsModel
import live.foodclub.localdatasource.room.dao.UserDetailsDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDetailsLocalDataSourceImpl @Inject constructor(
    private val userDetailsDao: UserDetailsDao
) : UserDetailsLocalDataSource {

    override suspend fun insertLocalUserDetails(userDetails: UserDetailsModel) {
        return userDetailsDao.insertProfileData(userDetails)
    }

    override fun getLocalUserDetails(id: Long): Flow<UserDetailsModel> {
        return userDetailsDao.getLocalUserDetails(id)
    }

    override suspend fun updateLocalUserDetails(userDetails: UserDetailsModel) {
        return userDetailsDao.updateLocalUserDetails(userDetails)
    }

    override fun deleteLocalUserDetails(userDetails: UserDetailsModel) {
        return userDetailsDao.deleteLocalUserDetails(userDetails)
    }

    override fun clearLocalUserDetails() {
        return userDetailsDao.clearLocalUserDetailsData()
    }

}