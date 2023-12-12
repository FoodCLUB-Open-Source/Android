package android.kotlin.foodclub.room.repository.datasource

import android.kotlin.foodclub.room.entity.OfflineProfileVideosModel
import kotlinx.coroutines.flow.Flow

interface ProfileVideosDataLocalSource {

    suspend fun insertProfileVideosData(videosModel: OfflineProfileVideosModel)
    fun getProfileVideosData(id: Long): Flow<OfflineProfileVideosModel>
    suspend fun updateProfileVideosData(videosModel: OfflineProfileVideosModel)
    fun getAllProfileVideosData(): Flow<List<OfflineProfileVideosModel>>

}