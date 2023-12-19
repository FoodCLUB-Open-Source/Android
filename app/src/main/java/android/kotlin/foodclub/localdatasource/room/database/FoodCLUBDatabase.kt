package android.kotlin.foodclub.localdatasource.room.database

import android.kotlin.foodclub.localdatasource.room.entity.UserDetailsModel
import android.kotlin.foodclub.localdatasource.room.converters.Converters
import android.kotlin.foodclub.localdatasource.room.dao.UserDetailsDao
import android.kotlin.foodclub.localdatasource.room.dao.UserProfileVideosDao
import android.kotlin.foodclub.localdatasource.room.entity.OfflineProfileVideosModel
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