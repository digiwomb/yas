package dev.digiwomb.yas.model

import dev.digiwomb.yas.uuid.UuidV7Generator
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.util.UUID
import dev.digiwomb.yas.model.mapping.UsersTableV001 as UsersTable

@Entity
@Table(name = UsersTable.TABLE_NAME)
data class Users(
    @Id
    @GeneratedValue
    @UuidV7Generator
    val id: UUID? = null,

    @get:NotBlank
    @get:NotNull
    @Column(nullable = false, unique = true)
    val email: String = "",

    @get:NotBlank
    @get:NotNull
    @Column(nullable = false)
    val name: String = "",

    @get:NotBlank
    @get:NotNull
    @Column(nullable = false)
    val password: String = ""
)
