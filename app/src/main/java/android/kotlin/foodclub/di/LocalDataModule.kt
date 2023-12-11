package android.kotlin.foodclub.di

import android.kotlin.foodclub.room.db.ProfileDao
import android.kotlin.foodclub.room.repository.datasource.ProfileDataLocalSource
import android.kotlin.foodclub.room.repository.datasource_impl.ProfileDataLocalSourceImpl
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
    fun provideProfileLocalDataSource(profileDAO: ProfileDao): ProfileDataLocalSource {
        return ProfileDataLocalSourceImpl(profileDAO)
    }

}
