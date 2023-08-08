package sb.parma.infrastructure.config.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class JwtService(
    @Value("\${api.security.token.secret}")
    val secret: String,

    @Value("\${api.security.token.expiration}")
    val expiration: Long,

    @Value("\${spring.application.name}")
    val applicationName: String
) {

    fun generateToken(userDetails: UserDetails): String {
        return try {
             JWT.create()
                .withIssuer(applicationName)
                .withSubject(userDetails.username)
                .withExpiresAt(getExpirationDate())
                .sign(Algorithm.HMAC256(secret))
        }catch (exception: JWTCreationException) {
            throw JWTCreationException("Error while generating token.", exception)
        }
    }

    fun getExpirationDate(): Instant? {
        return LocalDateTime.now().plusMinutes(expiration).toInstant(ZoneOffset.of("-03:00"))
    }

    fun getSubject(token: String): String? {
        return try {
            JWT.require(Algorithm.HMAC256(secret))
                .withIssuer(applicationName)
                .build()
                .verify(token)
                .subject
        }catch (exception: JWTVerificationException) {
            ""
        }
    }

}