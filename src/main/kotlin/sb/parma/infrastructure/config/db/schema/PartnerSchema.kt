package sb.parma.infrastructure.config.db.schema

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.UUID

@Entity
@Table(name = "partner")
data class PartnerSchema(
    @Id
    @Column(name= "id_partner")
    val id: UUID,

    @Column(name= "email")
    val email: String,

    @Column(name= "password")
    val password: String,
): java.io.Serializable{}
