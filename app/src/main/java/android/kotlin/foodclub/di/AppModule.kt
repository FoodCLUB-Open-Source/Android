package android.kotlin.foodclub.di

import android.content.Context
import android.content.SharedPreferences
import android.kotlin.foodclub.network.retrofit.utils.auth.RefreshTokenManager
import android.kotlin.foodclub.domain.models.products.MyBasketCache
import android.kotlin.foodclub.network.retrofit.utils.SessionCache
import android.kotlin.foodclub.repositories.ProductRepository
import android.kotlin.foodclub.viewModels.home.myBasket.MyBasketViewModel
import android.kotlin.foodclub.utils.helpers.ConnectivityUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
    fun provideMyBasketViewModel(
        basketCache: MyBasketCache,
        productRepository: ProductRepository
    ): MyBasketViewModel = MyBasketViewModel(basketCache, productRepository)

    @Provides
    @Singleton
    fun provideConnectivityUtils(
        @ApplicationContext context: Context
    ): ConnectivityUtils = ConnectivityUtils(context)
}