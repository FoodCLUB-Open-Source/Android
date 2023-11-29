package android.kotlin.foodclub.room.repository.datasource

import android.kotlin.foodclub.room.entity.ProfileModel
import kotlinx.coroutines.flow.Flow

interface ProfileDataLocalSource {

    suspend fun insertData(profileModel: ProfileModel)
    fun getData(id: Long): Flow<ProfileModel>
    suspend fun updateData(profileModel: ProfileModel)

}