package dev.digiwomb.yas.model

import com.fasterxml.jackson.annotation.JsonIgnore
import dev.digiwomb.yas.uuid.UuidV7Generator
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.Instant
import java.util.UUID
import dev.digiwomb.yas.model.mapping.users.UsersTableV001 as UsersTable

@Entity
@Table(name = UsersTable.TABLE_NAME)
data class Users(
    @Id
    @GeneratedValue
    @UuidV7Generator
    @Column(name = UsersTable.COLUMN_ID)
    val id: UUID? = null,

    @get:NotBlank
    @get:NotNull
    @Column(name = UsersTable.COLUMN_EMAIL, nullable = false, unique = true)
    val email: String = "",

    @get:NotBlank
    @get:NotNull
    @Column(name = UsersTable.COLUMN_NAME, nullable = false)
    val name: String = "",

    @get:NotBlank
    @get:NotNull
    @JsonIgnore
    @Column(name = UsersTable.COLUMN_PASSWORD, nullable = false)
    val password: String = "",

    @Column(name = UsersTable.COLUMN_CREATED_AT, nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = UsersTable.COLUMN_UPDATED_AT, nullable = false)
    var updatedAt: Instant = Instant.now()
) {
    @PreUpdate
    fun onUpdate() {
        this.copy(updatedAt = Instant.now())
    }
}