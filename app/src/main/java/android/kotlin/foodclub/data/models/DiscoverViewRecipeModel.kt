package android.kotlin.foodclub.data.models

data class DiscoverViewRecipeModel (val name: String, var time: String){

    val userName:String
    val timeUploaded:String


    init{
        userName = name;
        timeUploaded = time;
    }

}