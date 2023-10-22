package android.kotlin.foodclub.network.retrofit.dtoMappers

import android.kotlin.foodclub.network.retrofit.dtoMappers.edamam.EdamamFoodProductsMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.posts.PostToVideoMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.recipes.RecipeMapper
import android.kotlin.foodclub.network.retrofit.dtoMappers.stories.StoryMapper
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
    fun provideEdamamFoodProductsMapper(): EdamamFoodProductsMapper {
        return EdamamFoodProductsMapper()
    }

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

    @Provides
    @Singleton
    fun provideRecipeMapper(): RecipeMapper {
        return RecipeMapper()
    }
}