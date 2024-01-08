package android.kotlin.foodclub.localdatasource.localdatasource.profile_local_datasource

import android.kotlin.foodclub.localdatasource.room.entity.OfflineProfileModel
import kotlinx.coroutines.flow.Flow

interface ProfileLocalDataSource {
    suspend fun insertProfileLocalData(offlineProfileModel: OfflineProfileModel)
    fun getProfileData(userId: Long): Flow<OfflineProfileModel>
    fun clearProfileData()
}