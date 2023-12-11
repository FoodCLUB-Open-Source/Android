package android.kotlin.foodclub.room.db

import android.kotlin.foodclub.room.entity.ProfileModel
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProfileModel::class], version = 2, exportSchema = false)
abstract class FoodCLUBDatabase: RoomDatabase() {
    abstract fun getProfileDao(): ProfileDao
}