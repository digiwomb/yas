package dev.digiwomb.yas.service

import dev.digiwomb.yas.exception.EmailExistsException
import dev.digiwomb.yas.exception.InvalidOldPasswordException
import dev.digiwomb.yas.exception.UserNotFoundException
import dev.digiwomb.yas.helper.OwnerService
import dev.digiwomb.yas.model.Authority
import dev.digiwomb.yas.model.Role
import dev.digiwomb.yas.model.User
import dev.digiwomb.yas.repository.UserRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val authorityService: AuthorityService
) : OwnerService<User> {

    fun findAll(): List<User> =
        userRepository.findAll()

    fun findById(id: UUID) : User = userRepository.findById(id).orElseThrow { UserNotFoundException(id.toString()) }

    fun findByEmail(email: String): User = userRepository.findByEmail(email)
        ?: throw UsernameNotFoundException("User not found: $email")

    fun create(user: User): User {

        val encryptedPassword = passwordEncoder.encode(user.password)
        val newUser = user.copy(
            password = encryptedPassword
        )

        return save(newUser)
    }

    fun deleteById(id: UUID) {

        val user = findById(id)
        userRepository.delete(user)
    }

    fun update(id: UUID, user: User): User {

        val updatedUser = user.copy(
            id = id,
            password = findById(id).password
        )

        return save(updatedUser)
    }

    fun changePassword(id: UUID, oldPassword: String, newPassword: String): User {

        val user = findById(id)
        if(passwordEncoder.matches(oldPassword, user.password)) {

            val updatedUser = user.copy(
                password = passwordEncoder.encode(newPassword)
            )
            return save(updatedUser)
        } else {
            throw InvalidOldPasswordException()
        }
    }

    fun findIdByEmail(email: String): UUID = findByEmail(email).id
        ?: throw InternalError()

    private fun save(user: User): User {

        try {
            return userRepository.save(user)
        } catch (
            ex: DataIntegrityViolationException
        ) {
            throw EmailExistsException(user.email)
        }
    }

    fun findAuthoritiesByUser(user: User): List<Authority> {

        val authorities = mutableListOf<Authority>()

        user.roles.forEach { role ->
            role.authorities.forEach { authority ->
                authorities.add(authority)
                authorities.addAll(authorityService.getAllChildren(authority))
            }
        }

        return authorities
    }

    override fun findAuthoritiesAsStringByUser(user: User): List<String> {

        val authorities = mutableListOf<String>()

        findAuthoritiesByUser(user).forEach { authority ->
            authorities.add(authority.name)
        }

        return authorities
    }

    override fun findUsernameByUser(user: User): String = user.email

    override fun findByUsername(username: String): User = findByEmail(username)
}