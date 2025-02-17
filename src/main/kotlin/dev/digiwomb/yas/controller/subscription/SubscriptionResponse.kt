package dev.digiwomb.yas.controller.subscription

import dev.digiwomb.yas.controller.user.UserResponse
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

data class SubscriptionResponse(
    val id: UUID,
    val title: String,
    val amount: BigDecimal,
    val user: UserResponse,
    val createdAt: Instant,
    val updatedAt: Instant
)
