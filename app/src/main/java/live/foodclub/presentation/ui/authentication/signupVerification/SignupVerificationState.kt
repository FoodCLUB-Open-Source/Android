package live.foodclub.presentation.ui.authentication.signupVerification

import live.foodclub.domain.enums.ApiCallStatus

data class SignupVerificationState(
    val status : ApiCallStatus,
    val message :String,
    val errorOccurred : Boolean,
    val username : String?,
    val password : String?
) {
    companion object {
        fun default() = SignupVerificationState(
            status = ApiCallStatus.DONE,
            message = "",
            errorOccurred = false,
            username = null,
            password = null
        )
    }
}
