package android.kotlin.foodclub.di

import android.kotlin.foodclub.localdatasource.room.dao.UserDetailsDao
import android.kotlin.foodclub.localdatasource.room.dao.UserProfileVideosDao
import android.kotlin.foodclub.localdatasource.localdatasource.userdetailslocaldatasource.UserDetailsLocalDataSource
import android.kotlin.foodclub.localdatasource.localdatasource.profilevideoslocaldatasource.ProfileVideosDataLocalSource
import android.kotlin.foodclub.localdatasource.localdatasource.userdetailslocaldatasource.UserDetailsLocalDataSourceImpl
import android.kotlin.foodclub.localdatasource.localdatasource.profilevideoslocaldatasource.ProfileVideosDataLocalSourceImpl
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
    fun provideUserDetailsLocalDataSource(userDetailsDao: UserDetailsDao): UserDetailsLocalDataSource {
        return UserDetailsLocalDataSourceImpl(userDetailsDao)
    }

    @Singleton
    @Provides
    fun provideVideosDataLocalSource(userProfileVideosDao: UserProfileVideosDao): ProfileVideosDataLocalSource {
        return ProfileVideosDataLocalSourceImpl(userProfileVideosDao)
    }
}
