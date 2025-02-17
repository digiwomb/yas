package dev.digiwomb.yas.controller.user

import dev.digiwomb.yas.exception.UserNotFoundException
import dev.digiwomb.yas.model.User
import dev.digiwomb.yas.service.UserService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/{id}")
    fun findById(@PathVariable id: UUID) : UserResponse = userService.findById(id).toResponse()
}