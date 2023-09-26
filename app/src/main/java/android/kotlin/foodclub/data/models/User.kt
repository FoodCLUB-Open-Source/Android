package android.kotlin.foodclub.data.models

data class User(
    val image: String,
    var userName: String,
    var fullName:String,
    var isFollower:Boolean,
    var isFollowing:Boolean
    ){}