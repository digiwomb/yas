package dev.digiwomb.yas.controller

import java.time.LocalDateTime

data class ErrorResponse(
    val type: String,
    val timestamp: LocalDateTime,
    val error: String,
    val status: Int,
    val errors: Map<String, String>?,
    val detail: String?,
    val path: String,
)