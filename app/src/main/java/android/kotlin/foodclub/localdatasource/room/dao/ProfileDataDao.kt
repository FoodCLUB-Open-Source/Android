package android.kotlin.foodclub.localdatasource.room.dao

import android.kotlin.foodclub.localdatasource.room.entity.OfflineProfileModel
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfileData(offlineProfileModel: OfflineProfileModel)

    @Query("SELECT * FROM profile_data WHERE userId=:id")
    fun getProfileData(id: Long): Flow<OfflineProfileModel>

    @Query("DELETE FROM profile_data")
    fun clearProfileData()

}