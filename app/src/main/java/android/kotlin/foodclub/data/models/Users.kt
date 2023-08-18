package android.kotlin.foodclub.data.models

data class Users(val image: String, var userName: String,var fullName:String){


    val objectImage:String
    val objectUserName:String
    val objectFullName:String


    init{
        objectImage = userName
        objectUserName = userName;
        objectFullName = fullName;
    }



}