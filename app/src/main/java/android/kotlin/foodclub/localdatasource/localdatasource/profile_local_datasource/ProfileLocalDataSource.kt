package android.kotlin.foodclub.localdatasource.localdatasource.profile_local_datasource

import android.kotlin.foodclub.localdatasource.room.entity.ProfileEntity
import kotlinx.coroutines.flow.Flow

interface ProfileLocalDataSource {
    suspend fun insertProfileLocalData(profileEntity: ProfileEntity)
    fun getProfileData(userId: Long): Flow<ProfileEntity>
    fun clearProfileData()
}