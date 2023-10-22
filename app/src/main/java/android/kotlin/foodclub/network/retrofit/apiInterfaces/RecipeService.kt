package android.kotlin.foodclub.network.retrofit.apiInterfaces

import android.kotlin.foodclub.network.retrofit.dtoModels.recipes.RecipeDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface RecipeService {
    @POST("posts/{user_id}")
    suspend fun createRecipe(
        @Path("user_id") userId: String,
        @Body recipe: RecipeDto
    ): Response<Unit>
}