package android.kotlin.foodclub.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.kotlin.foodclub.api.authentication.API
import android.kotlin.foodclub.utils.helpers.MyBasketCache
import android.kotlin.foodclub.utils.helpers.SessionCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    @Named("sessionPreferences")
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("session_prefs", MODE_PRIVATE);
    }

    @Provides
    @Singleton
    fun provideSessionCache(@Named("sessionPreferences") sessionPreferences: SharedPreferences): SessionCache {
        return SessionCache(sessionPreferences)
    }

    @Provides
    @Singleton
    @Named("basketPreferences")
    fun provideBasketPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("basket_prefs", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideBasket(@Named("basketPreferences") basketPreferences: SharedPreferences): MyBasketCache {
        return MyBasketCache(basketPreferences)
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