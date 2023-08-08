package sb.parma.useCase.authentication.authenticate

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import sb.parma.infrastructure.config.security.JwtService
import sb.parma.useCase.authentication.AuthenticationOutput

@Service
class AuthenticateUseCase(
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager
) {

    operator fun invoke(payload: AuthenticateInput): AuthenticationOutput {
        val partner = UsernamePasswordAuthenticationToken(payload.email, payload.password)
        val auth = this.authenticationManager.authenticate(partner)
        val userDetails = auth.principal as UserDetails
        return AuthenticationOutput(jwtService.generateToken(userDetails))
    }

}