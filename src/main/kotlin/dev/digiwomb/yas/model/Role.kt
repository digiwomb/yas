package dev.digiwomb.yas.model

import dev.digiwomb.yas.helper.annotation.uuid.UuidV7Generator
import dev.digiwomb.yas.model.mapping.role.RoleAuthorityTableV001 as RoleAuthorityTable
import jakarta.persistence.Column
import dev.digiwomb.yas.model.mapping.role.RoleTableV001 as RoleTable
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
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
    val authorities: Set<Authority> = emptySet()
)
