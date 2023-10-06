package android.kotlin.foodclub.network.retrofit.apiInterfaces

import android.kotlin.foodclub.network.retrofit.dtoModels.EdamamFoodProductsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsService {
    @GET("api/food-database/v2/parser")
    suspend fun getFoodProducts(
        @Query("app_id") appId: String,
        @Query("app_key") appKey: String,
        @Query("ingr") searchString: String
    ): Response<EdamamFoodProductsDto>

}