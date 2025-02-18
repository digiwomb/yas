package dev.digiwomb.yas.model

import com.fasterxml.jackson.annotation.JsonIgnore
import dev.digiwomb.yas.controller.user.UserResponse
import dev.digiwomb.yas.helper.annotation.uuid.UuidV7Generator
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.DynamicUpdate
import java.time.Instant
import java.util.UUID
import dev.digiwomb.yas.model.mapping.user.UserTableV001 as UserTable
import dev.digiwomb.yas.model.mapping.subscription.SubscriptionTableV001 as SubscriptionTable

@DynamicUpdate
@Entity
@Table(name = UserTable.TABLE_NAME)
data class User(
    @Id
    @GeneratedValue
    @UuidV7Generator
    @Column(name = UserTable.COLUMN_ID, updatable = false, unique = false, nullable = true)
    val id: UUID? = null,

    @get:NotBlank
    @get:NotNull
    @Column(name = UserTable.COLUMN_EMAIL, nullable = false, unique = true)
    var email: String = "",

    @get:NotBlank
    @get:NotNull
    @Column(name = UserTable.COLUMN_NAME, nullable = false)
    var name: String = "",

    @get:NotBlank
    @get:NotNull
    @Column(name = UserTable.COLUMN_PASSWORD, nullable = false)
    var password: String = "",

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var subscriptions: MutableList<Subscription> = mutableListOf(),

    @Column(name = UserTable.COLUMN_CREATED_AT, nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = UserTable.COLUMN_UPDATED_AT, nullable = false)
    var updatedAt: Instant = Instant.now()
) {
    internal fun toResponse(): UserResponse =
        UserResponse(
            id = this.id ?: throw IllegalStateException("User id is null"),
            email = this.email,
            name = this.name,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )

    @PreUpdate
    fun onUpdate() {
        this.updatedAt = Instant.now()
    }
}