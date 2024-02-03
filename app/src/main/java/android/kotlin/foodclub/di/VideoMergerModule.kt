package android.kotlin.foodclub.di

import android.content.Context
import android.kotlin.foodclub.utils.helpers.ffmpeg.VideoMerger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object VideoMergerModule {

    @Provides
    fun provideVideoMerger(@ApplicationContext context: Context): VideoMerger {
        return VideoMerger(context)
    }
}
