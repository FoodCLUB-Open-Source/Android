package android.kotlin.foodclub.localdatasource.localdatasource.userdetailslocaldatasource

import android.kotlin.foodclub.localdatasource.room.entity.UserDetailsModel
import kotlinx.coroutines.flow.Flow

interface UserDetailsLocalDataSource {

    suspend fun insertProfile(userDetails: UserDetailsModel)
    fun getProfile(id: Long): Flow<UserDetailsModel?>
    suspend fun updateProfile(userDetails: UserDetailsModel)

    fun deleteProfile(userDetails: UserDetailsModel)

    fun clearProfiles()

}