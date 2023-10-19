package android.kotlin.foodclub.network.retrofit.utils.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException

class JWTManager {
    companion object {
        fun createJWT(userId: Long): String? {
            try {
                val algorithm: Algorithm = Algorithm.HMAC512("YZ6CFAKWCVLLFL8RKQDA2CZKEKUN7UFH")
                return JWT.create()
                    .withClaim("userId", userId)
                    .withClaim("expiryAt", System.currentTimeMillis() + 3600000)
                    .withIssuer("FoodCLUB")
                    .sign(algorithm)
            } catch (exception: JWTCreationException) {
                // Invalid Signing configuration / Couldn't convert Claims.
            }
            return null
        }
    }
}