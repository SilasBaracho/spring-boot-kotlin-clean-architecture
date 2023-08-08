package sb.parma.infrastructure.config.security

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import sb.parma.domain.partner.gateway.PartnerGateway

@Service
class AuthenticationService(
    val partnerGateway: PartnerGateway
): UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails? {
        return partnerGateway.findByEmail(username)
            .orElseThrow { UsernameNotFoundException("Partner $username not found") }
            .let { UserDetail(it) }
    }
}