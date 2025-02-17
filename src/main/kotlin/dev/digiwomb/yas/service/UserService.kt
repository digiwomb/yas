package dev.digiwomb.yas.service

import dev.digiwomb.yas.exception.UserNotFoundException
import dev.digiwomb.yas.model.User
import dev.digiwomb.yas.repository.UserRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun findById(id: UUID) : User = userRepository.findById(id).orElseThrow { UserNotFoundException(id.toString()) }

    fun findByEmail(email: String): User = userRepository.findByEmail(email)
        ?: throw UsernameNotFoundException("User not found: $email")
}