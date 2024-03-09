package android.kotlin.foodclub.domain.models.session

import android.util.Log
import com.auth0.android.jwt.JWT

class Session(val accessToken: String, val idToken: String) {
    var sessionUser: SessionUser = SessionUser("", 0)
        private set

    init {
        decodeToken()
        Log.d("Session", "token: $accessToken")
        Log.d("Session", "userId: ${sessionUser.username}")
        Log.d("Session", "expiryAt: ${sessionUser.expiryAt}")
    }

    private fun decodeToken() {
        val jwt = JWT(accessToken)
        val userId = jwt.getClaim("username")
        val expiryAt = jwt.getClaim("exp")
        sessionUser = SessionUser(userId.asString() ?: "", expiryAt.asLong() ?: 0)
    }
}
