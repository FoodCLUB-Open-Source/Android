package com.example.foodclub.viewmodels.home

import android.content.Intent
import android.kotlin.foodclub.data.models.MyRecipeModel
import android.kotlin.foodclub.views.home.FollowerFollowingView
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController

class ProfileViewModel() : ViewModel() {

    var data = arrayListOf<MyRecipeModel>()
  //  var navController = navHostController

    fun getData(){
        data.add(MyRecipeModel("ifjak","12"))
        data.add(MyRecipeModel("ifjak","12"))
        data.add(MyRecipeModel("ifjak","12"))
        data.add(MyRecipeModel("ifjak","12"))
        data.add(MyRecipeModel("ifjak","12"))
    }

    fun showFollowers(){
      //  navController.navigate("FOLLOWER_VIEW")
    }

    fun showFollowings(){
        // Jump to FollowersFollowingActivity
    }


}
