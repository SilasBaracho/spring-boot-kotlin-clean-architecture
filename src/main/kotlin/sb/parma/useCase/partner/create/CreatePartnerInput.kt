package sb.parma.useCase.partner.create

import jakarta.validation.constraints.Email

data class CreatePartnerInput(
    @field:Email(message = "Email invalid")
    val email: String,
    val password: String
): java.io.Serializable{}
