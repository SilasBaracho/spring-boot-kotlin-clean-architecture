package sb.parma.infrastructure.config.db.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import sb.parma.infrastructure.config.db.schema.PartnerSchema
import java.util.Optional
import java.util.UUID

@Repository
interface PartnerRepository: JpaRepository<PartnerSchema, UUID> {

    fun findByEmail(email: String): Optional<PartnerSchema>

}