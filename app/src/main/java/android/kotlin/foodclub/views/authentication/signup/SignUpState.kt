package android.kotlin.foodclub.views.authentication.signup

import android.kotlin.foodclub.domain.enums.ApiCallStatus
import android.kotlin.foodclub.domain.models.auth.SignUpUser

data class SignUpState(
    val error: String,
    val title: String,
    val userSignUpInformation : SignUpUser,
    val repeatedEmail : String,
    val status : ApiCallStatus
) {
    companion object {
        fun default() = SignUpState(
            error = "",
            title = "",
            userSignUpInformation = SignUpUser("", "", "", ""),
            repeatedEmail = "",
            status = ApiCallStatus.DONE
        )
    }
}

