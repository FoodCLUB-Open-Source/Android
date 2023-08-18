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

    var data = arrayListOf<MyRecipeModel>()
    var data1 = arrayListOf<MyRecipeModel>()

    var myRecipeSliderColor by mutableStateOf(Color(203, 203, 203,255))
    var bookmarkedSliderColor by mutableStateOf(Color(203, 203, 203,255))
    var myRecipeTextDecoration by mutableStateOf(TextDecoration.None)
    var bookmarkedTextDecoration by mutableStateOf(TextDecoration.None)



    fun getData(){
        data.add(MyRecipeModel("ifjak","12"))
        data.add(MyRecipeModel("ifjak","12"))
        data.add(MyRecipeModel("ifjak","12"))
        data.add(MyRecipeModel("ifjak","12"))
        data.add(MyRecipeModel("ifjak","12"))

        data1.add(MyRecipeModel("Book","11"))
        data1.add(MyRecipeModel("Book","112"))
        data1.add(MyRecipeModel("Book","112"))

    }

    fun getPages(): List<ArrayList<MyRecipeModel>> {
        var pages = listOf(
            data,data1
        )

        return pages;
    }

    fun showFollowers(){
      //  navController.navigate("FOLLOWER_VIEW")
    }

    fun showFollowings(){
        // Jump to FollowersFollowingActivity
    }

    fun changeMyRecipeSliderColor(){

        myRecipeSliderColor = Color.Black
        myRecipeTextDecoration = TextDecoration.Underline
    }

    fun changeBookmarkedSliderColor(){
        bookmarkedSliderColor = Color.Black
        bookmarkedTextDecoration = TextDecoration.Underline
    }

    fun reverseMyRecipeSliderColor(){

        myRecipeSliderColor = Color(203, 203, 203,255)
        myRecipeTextDecoration = TextDecoration.None
    }

    fun reverseBookmarkedSliderColor(){
        bookmarkedSliderColor = Color(203, 203, 203,255)
        bookmarkedTextDecoration = TextDecoration.None
    }



}
