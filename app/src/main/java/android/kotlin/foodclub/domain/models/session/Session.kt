package android.kotlin.foodclub.domain.models.session

import android.util.Log
import com.auth0.android.jwt.JWT

class Session(val accessToken: String, val idToken: String, userId: Long) {
    var sessionUser: SessionUser = SessionUser("", userId, 0)
        private set

    init {
        decodeToken()
        Log.d("Session", "token: $accessToken")
        Log.d("Session", "username: ${sessionUser.username}")
        Log.d("Session", "expiryAt: ${sessionUser.expiryAt}")
    }

    private fun decodeToken() {
        val jwt = JWT(accessToken)
        val username = jwt.getClaim("username")
        val expiryAt = jwt.getClaim("exp")
        sessionUser = SessionUser(
            username.asString() ?: "", sessionUser.userId, expiryAt.asLong() ?: 0
        )
    }
}
