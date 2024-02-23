package android.kotlin.foodclub.di

import android.kotlin.foodclub.utils.helpers.firebase.NotificationService
import android.kotlin.foodclub.utils.helpers.firebase.PushNotificationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    @Provides
    @Singleton
    fun provideNotificationService(): NotificationService {
        return PushNotificationService()
    }

}