package android.kotlin.foodclub.data.models

import com.auth0.android.jwt.JWT

class Session(val token: String) {
    var sessionUser: SessionUser? = null
        private set

    init {
        decodeToken()
    }

    private fun decodeToken() {
        val jwt = JWT(token)
        val userId = jwt.getClaim("userId")
        val expiryAt = jwt.getClaim("expiryAt")
        sessionUser = SessionUser(userId.asLong() ?: 0, expiryAt.asLong() ?: 0)
    }
}
