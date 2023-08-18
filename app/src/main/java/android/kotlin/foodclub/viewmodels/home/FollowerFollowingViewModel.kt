package android.kotlin.foodclub.viewmodels.home

import android.kotlin.foodclub.data.models.MyRecipeModel
import android.kotlin.foodclub.data.models.Users
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class FollowerFollowingViewModel :ViewModel(){


    var data = arrayListOf<Users>()

    fun getData(){
        data.add(Users("ifjak","shubhamjain","Shubham"))
        data.add(Users("ifjak","shubhamjain","Shubham"))
        data.add(Users("ifjak","shubhamjain","Shubham"))
        data.add(Users("ifjak","shubhamjain","Shubham"))
        data.add(Users("ifjak","shubhamjain","Shubham"))
    }

    fun backButton(){


    }



}