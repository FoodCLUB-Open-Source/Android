package android.kotlin.foodclub.room.db

import android.kotlin.foodclub.domain.models.profile.UserDetailsModel
import android.kotlin.foodclub.room.converters.Converters
import android.kotlin.foodclub.room.dao.UserDetailsDao
import android.kotlin.foodclub.room.dao.UserProfileVideosDao
import android.kotlin.foodclub.room.entity.OfflineProfileModel
import android.kotlin.foodclub.room.entity.OfflineProfileVideosModel
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        UserDetailsModel::class,
        OfflineProfileVideosModel::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class FoodCLUBDatabase : RoomDatabase() {
    abstract fun getUserDetailsDao(): UserDetailsDao
    abstract fun getProfileVideosDao(): UserProfileVideosDao
}