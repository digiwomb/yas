package dev.digiwomb.yas.controller.authentication

data class AuthenticationRequest(
    val email: String,
    val password: String,
)