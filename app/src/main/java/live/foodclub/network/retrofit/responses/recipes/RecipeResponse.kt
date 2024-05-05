package live.foodclub.network.retrofit.responses.recipes

import live.foodclub.network.retrofit.dtoModels.recipes.RecipeDto
import androidx.annotation.Keep

@Keep
data class RecipeResponse(val recipe: RecipeDto)