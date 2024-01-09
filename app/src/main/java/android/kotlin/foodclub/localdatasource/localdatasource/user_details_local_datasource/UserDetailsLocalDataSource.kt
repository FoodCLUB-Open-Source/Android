package android.kotlin.foodclub.localdatasource.localdatasource.user_details_local_datasource

import android.kotlin.foodclub.localdatasource.room.entity.UserDetailsModel
import kotlinx.coroutines.flow.Flow

interface UserDetailsLocalDataSource {

    suspend fun insertLocalUserDetails(userDetails: UserDetailsModel)
    fun getLocalUserDetails(id: Long): Flow<UserDetailsModel?>
    suspend fun updateLocalUserDetails(userDetails: UserDetailsModel)

    fun deleteLocalUserDetails(userDetails: UserDetailsModel)

    fun clearLocalUserDetails()

}