package live.foodclub.network.retrofit.dtoMappers

import live.foodclub.network.retrofit.dtoMappers.posts.PostToVideoMapper
import live.foodclub.network.retrofit.dtoMappers.stories.StoryMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MappersModule {
    @Provides
    @Singleton
    fun providePostToVideoMapper(): PostToVideoMapper {
        return PostToVideoMapper()
    }

    @Provides
    @Singleton
    fun provideStoryMapper(): StoryMapper {
        return StoryMapper()
    }
}