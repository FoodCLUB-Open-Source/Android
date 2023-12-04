package android.kotlin.foodclub.room.db

import android.kotlin.foodclub.room.entity.ProfileModel
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProfileData(profileModel: ProfileModel)

    @Query("SELECT * FROM profile_data where userId=:id")
    fun getProfileData(id: Long): Flow<ProfileModel>

    @Update
    suspend fun updateProfileData(profile: ProfileModel)

}