package sb.parma.useCase.partner

import sb.parma.domain.partner.model.Partner
import java.util.UUID

data class PartnerOutput(
    val id: UUID,
    val email: String,
): java.io.Serializable{
    constructor(partner: Partner) : this (
        id = partner.id,
        email = partner.email
    )
}
