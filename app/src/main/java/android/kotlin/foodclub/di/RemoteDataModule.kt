package android.kotlin.foodclub.di

import android.kotlin.foodclub.network.remotedatasource.product.ProductRemoteDataSource
import android.kotlin.foodclub.network.remotedatasource.product.ProductRemoteDataSourceImpl
import android.kotlin.foodclub.network.remotedatasource.profile_remote_datasource.ProfileRemoteDataSource
import android.kotlin.foodclub.network.remotedatasource.profile_remote_datasource.ProfileRemoteDataSourceImpl
import android.kotlin.foodclub.network.remotedatasource.settings_remote_datasource.SettingsRemoteDataSource
import android.kotlin.foodclub.network.remotedatasource.settings_remote_datasource.SettingsRemoteDataSourceImpl
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

    @Binds
    abstract fun bindProfileRemoteDataSource(
        profileRemoteDataSourceImpl: ProfileRemoteDataSourceImpl
    ): ProfileRemoteDataSource

    @Binds
    abstract fun bindProductRemoteDataSource(
        productRemoteDataSourceImpl: ProductRemoteDataSourceImpl
    ): ProductRemoteDataSource

}