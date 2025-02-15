package dev.digiwomb.yas.model

import com.fasterxml.jackson.annotation.JsonIgnore
import dev.digiwomb.yas.model.uuid.UuidV7Generator
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.Instant
import java.util.UUID
import dev.digiwomb.yas.model.mapping.user.UserTableV001 as UserTable

@Entity
@Table(name = UserTable.TABLE_NAME)
data class User(
    @Id
    @GeneratedValue
    @UuidV7Generator
    @Column(name = UserTable.COLUMN_ID)
    val id: UUID? = null,

    @get:NotBlank
    @get:NotNull
    @Column(name = UserTable.COLUMN_EMAIL, nullable = false, unique = true)
    val email: String = "",

    @get:NotBlank
    @get:NotNull
    @Column(name = UserTable.COLUMN_NAME, nullable = false)
    val name: String = "",

    @get:NotBlank
    @get:NotNull
    @JsonIgnore
    @Column(name = UserTable.COLUMN_PASSWORD, nullable = false)
    val password: String = "",

    @Column(name = UserTable.COLUMN_CREATED_AT, nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = UserTable.COLUMN_UPDATED_AT, nullable = false)
    var updatedAt: Instant = Instant.now()
) {
    @PreUpdate
    fun onUpdate() {
        this.copy(updatedAt = Instant.now())
    }
}