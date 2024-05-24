package live.foodclub.di

import android.content.Context
import android.content.SharedPreferences
import live.foodclub.network.retrofit.utils.auth.RefreshTokenManager
import live.foodclub.domain.models.products.MyBasketCache
import live.foodclub.network.retrofit.utils.SessionCache
import live.foodclub.repositories.ProductRepository
import live.foodclub.viewModels.home.myBasket.MyBasketViewModel
import live.foodclub.utils.helpers.ConnectivityUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import live.foodclub.network.remotedatasource.posts.provider.PostsRemoteDataSourceProvider
import live.foodclub.network.remotedatasource.posts.provider.PostsRemoteDataSourceProviderImpl
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideSessionCache(@Named("sessionPreferences") sessionPreferences: SharedPreferences): SessionCache {
        return SessionCache(sessionPreferences)
    }

    @Provides
    @Singleton
    fun provideRefreshToken(
        @Named("refreshTokenPreferences") refreshTokenPreferences: SharedPreferences
    ): RefreshTokenManager {
        return RefreshTokenManager(refreshTokenPreferences)
    }

    @Provides
    @Singleton
    fun provideBasket(
        @Named("basketPreferences") basketPreferences: SharedPreferences,
        sessionCache: SessionCache
    ): MyBasketCache {
        return MyBasketCache(basketPreferences, sessionCache)
    }

    @Provides
    @Singleton
    fun provideConnectivityUtils(
        @ApplicationContext context: Context
    ): ConnectivityUtils = ConnectivityUtils(context)

    @Provides
    @Singleton
    fun providePostsRemoteDataSourceProvider(
        postsRemoteDataSourceProviderImpl: PostsRemoteDataSourceProviderImpl
    ): PostsRemoteDataSourceProvider {
        return postsRemoteDataSourceProviderImpl
    }
}