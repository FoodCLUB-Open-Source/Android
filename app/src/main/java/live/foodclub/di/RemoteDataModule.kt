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
import live.foodclub.network.remotedatasource.posts.annotations.BookmarkRemoteDataSource
import live.foodclub.network.remotedatasource.posts.annotations.DiscoverRemoteDataSource
import live.foodclub.network.remotedatasource.posts.sources.HomePostsDataSource
import live.foodclub.network.remotedatasource.posts.sources.PostsRemoteDataSource
import live.foodclub.network.remotedatasource.posts.annotations.HomeRemoteDataSource
import live.foodclub.network.remotedatasource.posts.annotations.ProfilePostsRemoteDataSource
import live.foodclub.network.remotedatasource.posts.sources.BookmarkPostsDataSource
import live.foodclub.network.remotedatasource.posts.sources.DiscoverPostsDataSource
import live.foodclub.network.remotedatasource.posts.sources.ProfilePostsDataSource

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

    @HomeRemoteDataSource
    @Binds
    abstract fun bindHomePostsRemoteDataSource(
        postsRemoteDataSourceImpl: HomePostsDataSource
    ): PostsRemoteDataSource

    @DiscoverRemoteDataSource
    @Binds
    abstract fun bindDiscoverPostsRemoteDataSource(
        postsRemoteDataSourceImpl: DiscoverPostsDataSource
    ): PostsRemoteDataSource

    @ProfilePostsRemoteDataSource
    @Binds
    abstract fun bindProfilePostsRemoteDataSource(
        postsRemoteDataSourceImpl: ProfilePostsDataSource
    ): PostsRemoteDataSource

    @BookmarkRemoteDataSource
    @Binds
    abstract fun bindBookmarkPostsRemoteDataSource(
        postsRemoteDataSourceImpl: BookmarkPostsDataSource
    ): PostsRemoteDataSource
}