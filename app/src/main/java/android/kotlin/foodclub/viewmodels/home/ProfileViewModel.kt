package com.example.foodclub.viewmodels.home

import android.content.Intent
import android.kotlin.foodclub.data.models.MyRecipeModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController

class ProfileViewModel() : ViewModel() {

    val tabItems = listOf(
        MyRecipeModel(
            "image", "0", true
        ), MyRecipeModel(
            "image", "0", false
        ), MyRecipeModel(
            "image", "0", true
        ), MyRecipeModel(
            "image", "0", false
        )
    )

    private fun getListFromDatabase() {

        /// Hardcoded List used, need to fetch from API---->


    }


    fun getListOfMyRecipes(): List<MyRecipeModel> {
        return tabItems;
    }

    fun getListOfBookmarkedRecipes(): List<MyRecipeModel> {

        return tabItems.filter { unit -> return@filter (unit.bookMarked == true) };

    }


}
