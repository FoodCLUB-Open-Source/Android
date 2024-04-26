package live.foodclub.repositories

import live.foodclub.domain.models.recipes.Recipe
import live.foodclub.domain.models.recipes.toRecipeDto
import live.foodclub.network.retrofit.services.RecipeService
import live.foodclub.network.retrofit.dtoModels.recipes.toRecipeModel
import live.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import live.foodclub.network.retrofit.responses.recipes.RecipeResponse
import live.foodclub.network.retrofit.utils.apiRequestFlow
import live.foodclub.utils.helpers.Resource

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

