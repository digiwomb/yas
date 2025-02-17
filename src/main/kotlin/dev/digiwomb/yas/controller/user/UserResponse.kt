package dev.digiwomb.yas.controller.user

import java.time.Instant
import java.util.UUID

data class UserResponse(
    val id: UUID,
    val email: String,
    val name: String,
    val createdAt: Instant,
    val updatedAt: Instant
)
