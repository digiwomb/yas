package dev.digiwomb.yas.controller.auth

data class AuthenticationRequest(
    val email: String,
    val password: String,
)