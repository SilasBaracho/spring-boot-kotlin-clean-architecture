package sb.parma.useCase.authentication.authenticate

data class AuthenticateInput(
    val email: String,
    val password: String
): java.io.Serializable{}
