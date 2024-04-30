package android.kotlin.foodclub.repositories

import android.kotlin.foodclub.domain.models.recipes.Recipe
import android.kotlin.foodclub.domain.models.recipes.toRecipeDto
import android.kotlin.foodclub.network.retrofit.services.RecipeService
import android.kotlin.foodclub.network.retrofit.dtoModels.recipes.toRecipeModel
import android.kotlin.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import android.kotlin.foodclub.network.retrofit.responses.recipes.RecipeResponse
import android.kotlin.foodclub.network.retrofit.utils.apiRequestFlow
import android.kotlin.foodclub.utils.helpers.Resource

class RecipeRepository(
    private val api: RecipeService
) {

    suspend fun createRecipe(recipe: Recipe): Boolean {
        return when(
            apiRequestFlow<Unit, DefaultErrorResponse> {
                api.createRecipe(recipe.toRecipeDto())
            }
        ) {
            is Resource.Success -> {
                true
            }

            is Resource.Error -> {
                false
            }
        }
    }

    suspend fun getRecipe(recipeId: Long): Resource<Recipe, DefaultErrorResponse> {
        return when(
            val resource = apiRequestFlow<RecipeResponse, DefaultErrorResponse> {
                api.getRecipe(recipeId)
            }
        ) {
            is Resource.Success -> {
                Resource.Success(
                    resource.data!!.body()!!.recipe.toRecipeModel()
                )
            }

            is Resource.Error -> {
                Resource.Error(resource.message!!)
            }
        }
    }
}

