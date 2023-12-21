package android.kotlin.foodclub.di

import android.kotlin.foodclub.network.remotedatasource.settingsremotedatasource.SettingsRemoteDataSource
import android.kotlin.foodclub.network.remotedatasource.settingsremotedatasource.SettingsRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataModule {

   @Binds
    abstract fun bindSettingsRemoteDataSource(
        settingsRemoteDataSourceImpl: SettingsRemoteDataSourceImpl
   ): SettingsRemoteDataSource
}