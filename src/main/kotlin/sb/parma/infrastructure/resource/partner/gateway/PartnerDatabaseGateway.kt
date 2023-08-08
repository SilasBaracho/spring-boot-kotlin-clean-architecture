package sb.parma.infrastructure.resource.partner.gateway

import org.springframework.stereotype.Component
import sb.parma.domain.partner.gateway.PartnerGateway
import sb.parma.domain.partner.model.Partner
import sb.parma.infrastructure.config.db.repository.PartnerRepository
import sb.parma.infrastructure.config.db.schema.PartnerSchema
import sb.parma.infrastructure.config.exception.NotFoundException
import java.util.Optional

@Component
class PartnerDatabaseGateway(
    private val partnerRepository: PartnerRepository
): PartnerGateway{

    override fun save(partner: Partner): Partner {
        return partnerRepository.save(
            PartnerSchema(partner.id, partner.email, partner.password)
        ).let {
            Partner(
                it.id,
                it.email,
                it.password
            )
        }
    }

    override fun findByEmail(email: String): Optional<Partner> {
        return partnerRepository.findByEmail(email).map { Partner(it.id, it.email, it.password) }
    }

    override fun findByEmailOrThrow(email: String): Partner? {
        return partnerRepository.findByEmail(email)
            .map { Partner(it.id, it.email, it.password) }
            .orElseThrow { NotFoundException("Partner not found") }
    }
}