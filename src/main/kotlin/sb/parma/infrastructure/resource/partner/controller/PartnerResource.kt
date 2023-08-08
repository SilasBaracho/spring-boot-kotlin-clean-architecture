package sb.parma.infrastructure.resource.partner.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import sb.parma.domain.partner.model.Partner
import sb.parma.useCase.partner.create.CreatePartnerInput
import sb.parma.useCase.partner.create.CreatePartnerUseCase
import sb.parma.infrastructure.utils.ConstantsUtils.ConstantConfig.PARTNER_URI_RESOURCE_V1
import sb.parma.useCase.partner.PartnerOutput

@RestController
@RequestMapping(PARTNER_URI_RESOURCE_V1)
class PartnerResource(
    val createPartnerUseCase: CreatePartnerUseCase
) {

    @PostMapping("create-account")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "Create a partner"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Partner created",
                content = [Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = PartnerOutput::class)
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid email or email is already registered",
                content = [Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE
                )]
            )
        ]
    )
    fun create(@RequestBody @Valid payload: CreatePartnerInput): PartnerOutput {
        return createPartnerUseCase.invoke(payload)
    }


}