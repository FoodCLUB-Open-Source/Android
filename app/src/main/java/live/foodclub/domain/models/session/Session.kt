package live.foodclub.domain.models.session

import androidx.annotation.Keep
import com.auth0.android.jwt.JWT

@Keep
class Session(val accessToken: String, val idToken: String, userId: Long) {
    var sessionUser: SessionUser = SessionUser("", userId, 0)
        private set

    init {
        decodeToken()
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
