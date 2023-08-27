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


    var data = arrayListOf<DiscoverViewRecipeModel>()
    var data1 = arrayListOf<DiscoverViewRecipeModel>()

    var categoriesColor by mutableStateOf(Color(203, 203, 203, 255))
    var worldColor by mutableStateOf(Color(203, 203, 203, 255))
    var myFridgeColor by mutableStateOf(Color(203, 203, 203, 255))
    var categoriesDecoration by mutableStateOf(TextDecoration.None)
    var worldDecoration by mutableStateOf(TextDecoration.None)
    var myFridgeDecoration by mutableStateOf(TextDecoration.None)

    var proteinColor by mutableStateOf(Color(203, 203, 203, 255))
    var carbsColor by mutableStateOf(Color(203, 203, 203, 255))
    var plantBasedColor by mutableStateOf(Color(203, 203, 203, 255))
    var drinksColor by mutableStateOf(Color(203, 203, 203, 255))
    var proteinDecoration by mutableStateOf(TextDecoration.None)
    var carbsDecoration by mutableStateOf(TextDecoration.None)
    var plantBasedDecoration by mutableStateOf(TextDecoration.None)
    var drinksDecoration by mutableStateOf(TextDecoration.None)


    fun getData() {
        data.add(DiscoverViewRecipeModel("Emily", "19 hours ago"))
        data.add(DiscoverViewRecipeModel("Emily", "19 hours ago"))
        data.add(DiscoverViewRecipeModel("Emily", "19 hours ago"))
        data.add(DiscoverViewRecipeModel("Emily", "19 hours ago"))
        data.add(DiscoverViewRecipeModel("Emily", "19 hours ago"))

        data1.add(DiscoverViewRecipeModel("Emily", "19 hours ago"))
        data1.add(DiscoverViewRecipeModel("Emily", "19 hours ago"))
        data1.add(DiscoverViewRecipeModel("Emily", "19 hours ago"))

    }

    fun getPages(): List<ArrayList<DiscoverViewRecipeModel>> {
        var pages = listOf(
            data, data1
        )

        return pages;
    }

    fun showFollowers() {
        //  navController.navigate("FOLLOWER_VIEW")
    }

    fun showFollowings() {
        // Jump to FollowersFollowingActivity
    }

//    fun changeMyRecipeSliderColor() {
//
//        myRecipeSliderColor = Color.Black
//        myRecipeTextDecoration = TextDecoration.Underline
//    }
//
//    fun changeBookmarkedSliderColor() {
//        bookmarkedSliderColor = Color.Black
//        bookmarkedTextDecoration = TextDecoration.Underline
//    }
//
//    fun reverseMyRecipeSliderColor() {
//
//        myRecipeSliderColor = Color(203, 203, 203, 255)
//        myRecipeTextDecoration = TextDecoration.None
//    }
//
//    fun reverseBookmarkedSliderColor() {
//        bookmarkedSliderColor = Color(203, 203, 203, 255)
//        bookmarkedTextDecoration = TextDecoration.None
//
//    }

    fun runUiChange(index:Int){

        for(i in 0..2){
             if(i == index){
                 myFridgeColor = Color.Black
                 myFridgeDecoration = TextDecoration.Underline
             }
        }

    }

    fun runUiChange1(index:Int){

        for(i in 0..3){
            if(i == index){
                myFridgeColor = Color.Black
                myFridgeDecoration = TextDecoration.Underline
            }
        }

    }

}
