package live.foodclub.localdatasource.room.dao

import live.foodclub.localdatasource.room.entity.ProfileEntity
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDataDao {

    @Upsert
    suspend fun insertProfileData(profileEntity: ProfileEntity)

    @Query("SELECT * FROM profile_data WHERE userId=:id")
    fun getProfileData(id: Long): Flow<ProfileEntity>

    @Query("DELETE FROM profile_data")
    fun clearProfileData()

}