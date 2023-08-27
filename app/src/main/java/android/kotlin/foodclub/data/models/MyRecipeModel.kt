package android.kotlin.foodclub.data.models

import android.media.Image

data class MyRecipeModel(val image: String, var likeCount: String) {

     val recipeImage:String
     val recipeLikeCount:String


     init{
          recipeImage = image;
          recipeLikeCount = likeCount;
     }



}