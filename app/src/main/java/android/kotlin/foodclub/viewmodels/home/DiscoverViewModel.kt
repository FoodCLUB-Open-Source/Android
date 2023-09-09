package com.example.foodclub.viewmodels.home

import android.kotlin.foodclub.data.models.DiscoverViewRecipeModel
import android.kotlin.foodclub.data.models.MyRecipeModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DiscoverViewModel : ViewModel() {


    val recipesList = listOf(
        DiscoverViewRecipeModel("Dwight","11 Hours","Protein","Germany"),
        DiscoverViewRecipeModel("Jim","10 Hours","Carbs","England"),
        DiscoverViewRecipeModel("Bob","9 Hours","Protein","France"),
        DiscoverViewRecipeModel("Michael","24 Hours","Protein","England"),
        DiscoverViewRecipeModel("Pam","12 Hours","Drinks","England"),
    )





}
