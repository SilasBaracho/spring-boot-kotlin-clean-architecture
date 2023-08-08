package sb.parma.useCase.partner.create

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import sb.parma.domain.partner.gateway.PartnerGateway
import sb.parma.domain.partner.model.Partner
import sb.parma.infrastructure.config.exception.BadRequestException
import sb.parma.useCase.partner.PartnerOutput

@Service
class CreatePartnerUseCase(
    val partnerGateway: PartnerGateway,
    private val passwordEncoder: PasswordEncoder
) {

    operator fun invoke(payload: CreatePartnerInput): PartnerOutput {
        this.emailExists(payload.email)

        val partner = Partner(
            email = payload.email,
            password = passwordEncoder.encode(payload.password)
        )

        return PartnerOutput(partnerGateway.save(partner))
    }

    fun emailExists(email: String){
        partnerGateway.findByEmail(email).let {
            if(it.isPresent)
                throw BadRequestException("Registered email, use another email or recover yours.")
        }
    }
}