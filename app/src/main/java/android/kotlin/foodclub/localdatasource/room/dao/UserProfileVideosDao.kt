package android.kotlin.foodclub.localdatasource.room.dao

import android.kotlin.foodclub.localdatasource.room.entity.OfflineProfileVideosModel
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileVideosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfileVideosData(videosModel: OfflineProfileVideosModel)

    @Query("SELECT * FROM profile_videos_data where videoId=:id")
    fun getProfileVideosData(id: Long): Flow<OfflineProfileVideosModel>

    @Update
    suspend fun updateProfileVideosData(videosModel: OfflineProfileVideosModel)

    @Query("SELECT * FROM profile_videos_data")
    fun getAllProfileVideosData(): Flow<List<OfflineProfileVideosModel>>

}