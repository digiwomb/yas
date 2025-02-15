package dev.digiwomb.yas.model

import dev.digiwomb.yas.model.uuid.UuidV7Generator
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID
import dev.digiwomb.yas.model.mapping.subscription.SubscriptionTableV001 as SubscriptionTable

@Entity
@Table(name = SubscriptionTable.TABLE_NAME)
data class Subscription(
    @Id
    @GeneratedValue
    @UuidV7Generator
    @Column(name = SubscriptionTable.COLUMN_ID)
    val id: UUID? = null,

    @get:NotBlank
    @get:NotNull
    @Column(name = SubscriptionTable.COLUMN_TITLE, nullable = false)
    val title: String = "",

    @get:NotNull
    @Column(name = SubscriptionTable.COLUMN_AMOUNT, nullable = false)
    val amount: BigDecimal = BigDecimal.ZERO,

    @get:NotNull
    @ManyToOne
    @JoinColumn(name = SubscriptionTable.COLUMN_USER_ID, nullable = false)
    val user: User? = null,

    @Column(name = SubscriptionTable.COLUMN_CREATED_AT, nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = SubscriptionTable.COLUMN_UPDATED_AT, nullable = false)
    var updatedAt: Instant = Instant.now()
) {
    @PreUpdate
    fun onUpdate() {
        this.copy(updatedAt = Instant.now())
    }
}