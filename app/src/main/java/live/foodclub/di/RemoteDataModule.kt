package live.foodclub.di

import live.foodclub.network.remotedatasource.product.ProductRemoteDataSource
import live.foodclub.network.remotedatasource.product.ProductRemoteDataSourceImpl
import live.foodclub.network.remotedatasource.profile_remote_datasource.ProfileRemoteDataSource
import live.foodclub.network.remotedatasource.profile_remote_datasource.ProfileRemoteDataSourceImpl
import live.foodclub.network.remotedatasource.settings_remote_datasource.SettingsRemoteDataSource
import live.foodclub.network.remotedatasource.settings_remote_datasource.SettingsRemoteDataSourceImpl
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