package android.kotlin.foodclub.api.responses

data class ForgotPasswordError(

    val protocol:String,
    val code:String,
    val message:String,
    val url:String


)
