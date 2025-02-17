package dev.digiwomb.yas.controller.user

import dev.digiwomb.yas.model.User
import jakarta.validation.constraints.NotBlank

data class UserRequestChangePassword(

    @field:NotBlank(message = "Old password must not be blank")
    val oldPassword: String,

    @field:NotBlank(message = "New password must not be blank")
    val newPassword: String
) {
}
