package dev.digiwomb.yas.controller.user

import dev.digiwomb.yas.model.User
import jakarta.validation.constraints.NotBlank

data class UserRequestUpdate(

    val email: String,

    val name: String
) {

    internal fun toModel(): User = User(
        email = this.email,
        name = this.name,
        password = ""
    )
}
