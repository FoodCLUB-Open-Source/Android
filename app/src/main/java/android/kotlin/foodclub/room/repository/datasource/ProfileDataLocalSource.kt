package android.kotlin.foodclub.room.repository.datasource

import android.kotlin.foodclub.room.entity.OfflineProfileModel
import kotlinx.coroutines.flow.Flow

interface ProfileDataLocalSource {

    suspend fun insertData(profileModel: OfflineProfileModel)
    fun getData(id: Long): Flow<OfflineProfileModel>
    suspend fun updateData(profileModel: OfflineProfileModel)

}