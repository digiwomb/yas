package dev.digiwomb.yas.model

import dev.digiwomb.yas.helper.annotation.uuid.UuidV7Generator
import jakarta.persistence.*
import dev.digiwomb.yas.model.mapping.role.RoleAuthorityTableV001 as RoleAuthorityTable
import dev.digiwomb.yas.model.mapping.role.RoleTableV001 as RoleTable
import org.hibernate.annotations.DynamicUpdate
import java.util.UUID

@Entity
@DynamicUpdate
@Table(name = RoleTable.TABLE_NAME)
data class Role(

    @Id
    @GeneratedValue
    @UuidV7Generator
    @Column(name = RoleTable.COLUMN_ID, nullable = false, unique = true, updatable = false)
    val id: UUID? = null,

    @Column(name = RoleTable.COLUMN_NAME, nullable = false, unique = true)
    val name: String = "",

    @ManyToMany
    @JoinTable(
        name = RoleAuthorityTable.TABLE_NAME,
        joinColumns = [JoinColumn(name = RoleAuthorityTable.COLUMN_ROLE_ID)],
        inverseJoinColumns = [JoinColumn(name = RoleAuthorityTable.COLUMN_AUTHORITY_ID)]
    )
    val authorities: MutableList<Authority> = mutableListOf()
)
