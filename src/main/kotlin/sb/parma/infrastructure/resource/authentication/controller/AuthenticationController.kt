package sb.parma.infrastructure.resource.authentication.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sb.parma.useCase.authentication.authenticate.AuthenticateInput
import sb.parma.useCase.authentication.authenticate.AuthenticateUseCase
import sb.parma.infrastructure.utils.ConstantsUtils.ConstantConfig.AUTHENTICATION_URI_RESOURCE_V1
import sb.parma.useCase.authentication.AuthenticationOutput
import sb.parma.useCase.partner.PartnerOutput

@RestController
@RequestMapping(AUTHENTICATION_URI_RESOURCE_V1)
class AuthenticationController(
    val authenticateUseCase: AuthenticateUseCase
) {

    @PostMapping
    @Operation(
        summary = "Authenticate a partner"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Authenticated partner",
                content = [Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = AuthenticationOutput::class)
                )]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Email or password is incorrect",
                content = [Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE
                )]
            )
        ]
    )
    fun authenticate(@RequestBody payload: AuthenticateInput): AuthenticationOutput {
        return authenticateUseCase.invoke(payload)
    }

}