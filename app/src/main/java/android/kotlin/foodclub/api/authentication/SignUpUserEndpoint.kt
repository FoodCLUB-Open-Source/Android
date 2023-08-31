package android.kotlin.foodclub.api.authentication

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query


data class UserSignUpInformation(
    val username: String,
    val email: String,
    val password: String,
)

data class LoginResponse(
    val id: Int,
    val username: String,
    val profile_picture: String
)

data class UserCredentials (
    val username: String,
    val password: String
)


interface API {

    @POST("login/signup")
    suspend fun postUser(
       @Query("username") name:String,@Query("email") email:String,@Query("password") password:String,
    ):Response<UserSignUpInformation>

    @POST("login/signin")

    suspend fun loginUser(
        @Body credentials: UserCredentials
    ): Response<LoginResponse>

}
