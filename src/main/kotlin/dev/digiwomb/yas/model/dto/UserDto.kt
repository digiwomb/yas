package dev.digiwomb.yas.model.dto

import java.time.Instant
import java.util.*

data class UserDto (
    val id: UUID,
    val email: String,
    val name: String,
    val password: String,
    val createdAt: Instant,
    val updatedAt: Instant
)
