package live.foodclub.network.retrofit.services

import live.foodclub.network.retrofit.dtoModels.recipes.RecipeDto
import live.foodclub.network.retrofit.responses.recipes.RecipeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RecipeService {
    @POST("posts")
    suspend fun createRecipe(
        @Body recipe: RecipeDto
    ): Response<Unit>

    @GET("recipe/{recipe_id}")
    suspend fun getRecipe(
        @Path("recipe_id") recipeId: Long
    ): Response<RecipeResponse>
}