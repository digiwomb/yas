package dev.digiwomb.yas.service.authentication

import dev.digiwomb.yas.repository.UserRepository
import dev.digiwomb.yas.service.UserService
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImplementation(private val userService: UserService) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val user = userService.findByEmail(email)

        return User.builder()
            .username(user.email)
            .password(user.password)
            .authorities(getAuthoritiesByEmail(email))
            .build()
    }

    private fun getAuthoritiesByEmail(email: String): List<GrantedAuthority> {

        val authorities = mutableListOf<GrantedAuthority>()

        userService.findAuthoritiesByUser(userService.findByEmail(email)).forEach() { authority ->
            authorities.add(SimpleGrantedAuthority(authority.name))
        }

        return authorities
    }
}
