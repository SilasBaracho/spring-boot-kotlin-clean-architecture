package sb.parma.config.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import sb.parma.domain.partner.gateway.PartnerGateway
import sb.parma.infrastructure.config.security.JwtService
import sb.parma.infrastructure.config.security.UserDetail

@Component
class SecurityFilter(
    val jwtService: JwtService,
    val partnerGateway: PartnerGateway
): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = recoverToken(request)
        if(token != null){
            val subject = jwtService.getSubject(token)
            val partner = partnerGateway.findByEmailOrThrow(subject!!)?.let { UserDetail(it) }
            val authentication = UsernamePasswordAuthenticationToken(partner, null, partner?.authorities)
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }

    private fun recoverToken(request: HttpServletRequest): String? {
        val authHeader = request.getHeader("Authorization") ?: return null
        return authHeader.replace("Bearer ", "")
    }
}