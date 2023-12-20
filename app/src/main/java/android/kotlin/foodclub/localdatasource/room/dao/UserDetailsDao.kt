package android.kotlin.foodclub.localdatasource.room.dao

import android.kotlin.foodclub.localdatasource.room.entity.UserDetailsModel
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfileData(profileModel: UserDetailsModel)

    @Query("SELECT * FROM user_details WHERE id=:id")
    fun getProfileData(id: Long): Flow<UserDetailsModel>

    @Update
    suspend fun updateProfileData(profile: UserDetailsModel)

    @Delete
    fun deleteProfileData(profile: UserDetailsModel)

    @Query("DELETE FROM user_details")
    fun clearProfileData()

}