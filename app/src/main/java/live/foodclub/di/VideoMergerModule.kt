package live.foodclub.di

import android.content.Context
import live.foodclub.utils.helpers.ffmpeg.VideoMerger
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
