package dev.digiwomb.yas.controller.user

data class UserRequest(
    val email: String,
    val name: String,
    val password: String
)
