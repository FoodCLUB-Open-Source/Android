package android.kotlin.foodclub.viewmodels.home

import android.kotlin.foodclub.data.models.User
import androidx.lifecycle.ViewModel

class FollowerFollowingViewModel :ViewModel(){

    fun getFollowersList():List<User>{
        val list = listOf<User>(
            User("","eric","Eric Young",true,true),
            User("","eric","Eric Young",true,true),
            User("","eric","Eric Young",true,true),
            User("","eric","Eric Young",true,true),
            User("","eric","Eric Young",true,true),
            User("","eric","Eric Young",true,true),
            User("","eric","Eric Young",true,true),
        )

        return list;
    }

    fun getFollowingList():List<User>{
        val list = listOf<User>(
            User("","eric","Eric Young",true,true),
            User("","eric","Eric Young",true,true),
            User("","eric","Eric Young",true,true),
            User("","eric","Eric Young",true,true),
            User("","eric","Eric Young",true,true),
            User("","eric","Eric Young",true,true),
            User("","eric","Eric Young",true,true),
        )

        return list;
    }

}