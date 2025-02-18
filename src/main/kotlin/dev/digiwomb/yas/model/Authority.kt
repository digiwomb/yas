package dev.digiwomb.yas.model

import dev.digiwomb.yas.helper.annotation.uuid.UuidV7Generator
import jakarta.persistence.Column
import dev.digiwomb.yas.model.mapping.authority.AuthorityTableV001 as AuthorityTable
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.DynamicUpdate
import java.util.UUID

@DynamicUpdate
@Entity
@Table(name = AuthorityTable.TABLE_NAME)
data class Authority (

    @Id
    @GeneratedValue
    @UuidV7Generator
    @Column(name = AuthorityTable.COLUMN_ID, nullable = false, unique = false, updatable = false)
    val id: UUID? = null,

    @get:NotBlank
    @get:NotNull
    @Column(name  = AuthorityTable.COLUMN_NAME, nullable = false, unique = true, updatable = false)
    val name: String = "",

    @ManyToOne
    @JoinColumn(name = AuthorityTable.COLUMN_PARENT)
    val parent: Authority? = null
)