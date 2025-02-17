package dev.digiwomb.yas.controller.user

import dev.digiwomb.yas.model.User
import jakarta.validation.constraints.NotBlank

data class UserRequestCreate(

    @field:NotBlank(message = "Email must not be blank")
    val email: String,

    @field:NotBlank(message = "Name must not be blank")
    val name: String,

    @field:NotBlank(message = "Password must not be blank")
    val password: String
) {

    internal fun toModel(): User = User(
        email = this.email,
        name = this.name,
        password = this.password
    )
}
