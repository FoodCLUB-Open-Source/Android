package live.foodclub.localdatasource.room.dao

import live.foodclub.localdatasource.room.entity.UserDetailsModel
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
    suspend fun insertProfileData(userDetailsModel: UserDetailsModel)

    @Query("SELECT * FROM user_details WHERE id=:id")
    fun getLocalUserDetails(id: Long): Flow<UserDetailsModel>

    @Update
    suspend fun updateLocalUserDetails(userDetailsModel: UserDetailsModel)

    @Delete
    fun deleteLocalUserDetails(userDetailsModel: UserDetailsModel)

    @Query("DELETE FROM user_details")
    fun clearLocalUserDetailsData()

}