package dev.digiwomb.yas.model

import dev.digiwomb.yas.controller.subscription.SubscriptionResponse
import dev.digiwomb.yas.exception.SubscriptionNotFoundException
import dev.digiwomb.yas.helper.OwnedEntity
import dev.digiwomb.yas.helper.annotation.uuid.UuidV7Generator
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.DynamicUpdate
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID
import dev.digiwomb.yas.model.mapping.subscription.SubscriptionTableV001 as SubscriptionTable

@DynamicUpdate
@Entity
@Table(name = SubscriptionTable.TABLE_NAME)
data class Subscription(
    @Id
    @GeneratedValue
    @UuidV7Generator
    @Column(name = SubscriptionTable.COLUMN_ID, updatable = false, nullable = false, unique = true)
    override val id: UUID? = null,

    @get:NotBlank
    @get:NotNull
    @Column(name = SubscriptionTable.COLUMN_TITLE, nullable = false)
    var title: String = "",

    @get:NotNull
    @Column(name = SubscriptionTable.COLUMN_AMOUNT, nullable = false)
    var amount: BigDecimal = BigDecimal.ZERO,

    @get:NotNull
    @ManyToOne
    @JoinColumn(name = SubscriptionTable.COLUMN_USER_ID, nullable = false, updatable = false)
    override val user: User,

    @Column(name = SubscriptionTable.COLUMN_CREATED_AT, nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = SubscriptionTable.COLUMN_UPDATED_AT, nullable = false)
    var updatedAt: Instant = Instant.now()
) : OwnedEntity<UUID, User>() {

    internal fun toResponse(user: User): SubscriptionResponse =
        SubscriptionResponse(
            id = this.id ?: throw IllegalStateException("Subscription id is null"),
            title = this.title,
            amount = this.amount,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            user = this.user.toResponse()
        )

    @PreUpdate
    fun onUpdate() {
        this.updatedAt = Instant.now()
    }
}