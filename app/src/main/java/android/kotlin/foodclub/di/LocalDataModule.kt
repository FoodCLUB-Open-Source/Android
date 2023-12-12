package android.kotlin.foodclub.di

import android.kotlin.foodclub.room.dao.UserDetailsDao
import android.kotlin.foodclub.room.dao.UserProfileVideosDao
import android.kotlin.foodclub.room.repository.datasource.ProfileDataLocalSource
import android.kotlin.foodclub.room.repository.datasource.ProfileVideosDataLocalSource
import android.kotlin.foodclub.room.repository.datasource_impl.ProfileDataLocalSourceImpl
import android.kotlin.foodclub.room.repository.datasource_impl.ProfileVideosDataLocalSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Singleton
    @Provides
    fun provideProfileLocalDataSource(userDetailsDao: UserDetailsDao): ProfileDataLocalSource {
        return ProfileDataLocalSourceImpl(userDetailsDao)
    }

    @Singleton
    @Provides
    fun provideVideosDataLocalSource(userProfileVideosDao: UserProfileVideosDao): ProfileVideosDataLocalSource {
        return ProfileVideosDataLocalSourceImpl(userProfileVideosDao)
    }
}
