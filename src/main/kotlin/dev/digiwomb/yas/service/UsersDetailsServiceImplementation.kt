package dev.digiwomb.yas.service

import dev.digiwomb.yas.repository.UsersRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UsersDetailsServiceImplementation(private val usersRepository: UsersRepository) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val user = usersRepository.findByEmail(email)
            .orElseThrow { UsernameNotFoundException("User not found: $email") }

        return User.builder()
            .username(user.email)
            .password(user.password)
            .roles("USER")
            .build()
    }
}
