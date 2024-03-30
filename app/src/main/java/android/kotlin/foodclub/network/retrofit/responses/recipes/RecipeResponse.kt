package android.kotlin.foodclub.network.retrofit.responses.recipes

import android.kotlin.foodclub.network.retrofit.dtoModels.recipes.RecipeDto
import androidx.annotation.Keep

@Keep
data class RecipeResponse(val recipe: RecipeDto)