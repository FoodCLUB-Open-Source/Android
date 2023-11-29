package android.kotlin.foodclub.di

import android.app.Application
import android.kotlin.foodclub.room.db.FoodCLUBDatabase
import android.kotlin.foodclub.room.db.ProfileDao
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideCoinDatabase(app: Application): FoodCLUBDatabase {
        return Room.databaseBuilder(
            app,
            FoodCLUBDatabase::class.java,
            "foodclub_room_database"
        )
            .fallbackToDestructiveMigration() // later change this to meet our intends
            .build()
    }

    @Provides
    @Singleton
    fun provideProfileDao(foodCLUBDatabase: FoodCLUBDatabase): ProfileDao {
        return foodCLUBDatabase.getProfileDao()
    }

}
