package android.kotlin.foodclub.network.retrofit.dtoMappers

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
}