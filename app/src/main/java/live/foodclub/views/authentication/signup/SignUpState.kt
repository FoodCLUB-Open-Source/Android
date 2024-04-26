package live.foodclub.views.authentication.signup

import live.foodclub.domain.enums.ApiCallStatus
import live.foodclub.domain.models.auth.SignUpUser

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

