package sb.parma.domain.partner.model

import java.util.UUID

data class Partner(
    val id: UUID = UUID.randomUUID(),
    val email: String,
    val password: String
): java.io.Serializable{}