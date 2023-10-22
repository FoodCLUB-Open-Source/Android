package android.kotlin.foodclub.domain.models.session

import android.util.Log
import com.auth0.android.jwt.JWT

class Session(val token: String) {
    var sessionUser: SessionUser = SessionUser(0, 0)
        private set

    init {
        decodeToken()
        Log.d("Session", "token: $token")
        Log.d("Session", "userId: ${sessionUser.userId}")
        Log.d("Session", "expiryAt: ${sessionUser.expiryAt}")
    }

    private fun decodeToken() {
        val jwt = JWT(token)
        val userId = jwt.getClaim("userId")
        val expiryAt = jwt.getClaim("expiryAt")
        sessionUser = SessionUser(userId.asLong() ?: 0, expiryAt.asLong() ?: 0)
    }
}
