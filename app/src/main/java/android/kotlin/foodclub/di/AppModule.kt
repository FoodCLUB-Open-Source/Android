package android.kotlin.foodclub.di

import android.content.SharedPreferences
import android.kotlin.foodclub.api.authentication.API
import android.kotlin.foodclub.network.retrofit.utils.auth.RefreshTokenManager
import android.kotlin.foodclub.utils.helpers.MyBasketCache
import android.kotlin.foodclub.network.retrofit.utils.SessionCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "http://ec2-35-177-0-50.eu-west-2.compute.amazonaws.com:3000/api/v1/"

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
    fun provideRetrofitApi(): API {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(API::class.java)
    }
}