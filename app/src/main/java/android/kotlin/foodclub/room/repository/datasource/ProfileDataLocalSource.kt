package android.kotlin.foodclub.room.repository.datasource

import android.kotlin.foodclub.domain.models.profile.UserDetailsModel
import android.kotlin.foodclub.room.entity.OfflineProfileModel
import kotlinx.coroutines.flow.Flow

interface ProfileDataLocalSource {

    suspend fun insertProfile(profileModel: UserDetailsModel)
    fun getProfile(id: Long): Flow<UserDetailsModel?>
    suspend fun updateProfile(profileModel: UserDetailsModel)

    fun deleteProfile(profileModel: UserDetailsModel)

    fun clearProfiles()

}