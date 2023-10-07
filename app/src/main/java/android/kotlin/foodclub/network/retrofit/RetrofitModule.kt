package android.kotlin.foodclub.network.retrofit

import android.kotlin.foodclub.network.retrofit.apiInterfaces.ProductsService
import android.kotlin.foodclub.network.retrofit.dtoMappers.EdamamFoodProductsMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    @Singleton
    fun provideEdamamFoodProductsMapper(): EdamamFoodProductsMapper {
        return EdamamFoodProductsMapper()
    }

    @Provides
    @Singleton
    fun provideEdamamService(): ProductsService {
        return Retrofit.Builder()
            .baseUrl("https://api.edamam.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductsService::class.java)
    }
}