package dev.digiwomb.yas.controller.user

import dev.digiwomb.yas.service.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val userService: UserService
) {

    @GetMapping("")
    fun findAll(): List<UserResponse> {
        return userService.findAll().map {
            it.toResponse()
        }
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: UUID) : UserResponse = userService.findById(id).toResponse()

    @GetMapping("/me")
    fun findLoggedInUser(
        @AuthenticationPrincipal userDetails: UserDetails
    ): UserResponse = userService.findByEmail(userDetails.username).toResponse()

    @PostMapping("")
    fun create(@Valid @RequestBody userRequestCreate: UserRequestCreate): UserResponse = userService.create(userRequestCreate.toModel()).toResponse()

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<Void> {

        userService.deleteById(id)
        return ResponseEntity.noContent().build()
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: UUID,
        @Valid @RequestBody userRequestUpdate: UserRequestUpdate
    ): UserResponse = userService.update(id, userRequestUpdate.toModel()).toResponse()

    @PutMapping("/me/password")
    fun updatePassword(
        @Valid @RequestBody userRequestChangePassword: UserRequestChangePassword,
        @AuthenticationPrincipal userDetails: UserDetails
    ): UserResponse {

        val userId = userService.findIdByEmail(userDetails.username)
        return userService.changePassword(userId, userRequestChangePassword.oldPassword, userRequestChangePassword.newPassword).toResponse()
    }
}