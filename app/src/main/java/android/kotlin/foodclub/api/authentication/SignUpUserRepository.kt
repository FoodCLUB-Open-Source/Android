package android.kotlin.foodclub.api.authentication

import android.kotlin.foodclub.api.retrofit.RetrofitInstance

class SignUpUserRepository {
    private val signUpUserService = RetrofitInstance.signUpUserService

    // need to change the List type since we're not doing a GET request, no need for a whole List when signup
    suspend fun postUser(): List<UserSignUpInformation> {
        return signUpUserService.postUser()
    }
}

