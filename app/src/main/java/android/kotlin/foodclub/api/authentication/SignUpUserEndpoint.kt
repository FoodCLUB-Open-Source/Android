package android.kotlin.foodclub.api.authentication

import retrofit2.http.POST

data class UserSignUpInformation(
    val username: String,
    val email: String,
    val password: String,
)

interface SignUpUserService {
    @POST("user")
    // need to change the List type since we're not doing a GET request, no need for a whole List when signup
    suspend fun postUser(): List<UserSignUpInformation>
}
