package android.kotlin.foodclub.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.kotlin.foodclub.utils.helpers.StoreData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {
    @Provides
    @Singleton
    @Named("sessionPreferences")
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("session_prefs", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    @Named("basketPreferences")
    fun provideBasketPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("basket_prefs", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    @Named("refreshTokenPreferences")
    fun provideSharedTokenPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("refresh_token_prefs", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideStoreData(@ApplicationContext context: Context): StoreData {
        return StoreData(context)
    }

}