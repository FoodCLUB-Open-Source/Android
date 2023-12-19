package android.kotlin.foodclub.room.dao

import android.kotlin.foodclub.room.entity.OfflineProfileModel
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfileData(profileModel: OfflineProfileModel)

    @Query("SELECT * FROM profile_data where userId=:id")
    fun getProfileData(id: Long): Flow<OfflineProfileModel>

    @Update
    suspend fun updateProfileData(profile: OfflineProfileModel)

}