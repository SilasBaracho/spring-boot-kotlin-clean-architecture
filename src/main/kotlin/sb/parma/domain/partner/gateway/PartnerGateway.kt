package sb.parma.domain.partner.gateway

import sb.parma.domain.partner.model.Partner
import java.util.Optional

interface PartnerGateway {
    fun save(partner: Partner): Partner

    fun findByEmail(email: String): Optional<Partner>

    fun findByEmailOrThrow(email: String): Partner?
}