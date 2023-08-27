package android.kotlin.foodclub.data.models

import android.media.Image

data class MyRecipeModel(
    val image: String,
    var likeCount: String,
    var bookMarked: Boolean
    ) {}