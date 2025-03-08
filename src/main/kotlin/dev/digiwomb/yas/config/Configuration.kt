package dev.digiwomb.yas.config

import dev.digiwomb.yas.repository.AuthorityRepository
import dev.digiwomb.yas.repository.RoleRepository
import dev.digiwomb.yas.repository.UserRepository
import dev.digiwomb.yas.service.AuthorityService
import dev.digiwomb.yas.service.RoleService
import dev.digiwomb.yas.service.UserService
import dev.digiwomb.yas.service.authentication.UserDetailsServiceImplementation
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableConfigurationProperties(JwtProperties::class)
class Configuration {

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authorityService(authorityRepository: AuthorityRepository) : AuthorityService = AuthorityService(authorityRepository)

    @Bean
    fun roleService(roleRepository: RoleRepository): RoleService = RoleService(roleRepository)

    @Bean
    fun userService(userRepository: UserRepository, passwordEncoder: BCryptPasswordEncoder, authorityRepository: AuthorityRepository, roleRepository: RoleRepository) : UserService = UserService(userRepository, passwordEncoder, authorityService(authorityRepository), roleService(roleRepository))

    @Bean
    fun userDetailsService(userRepository: UserRepository, passwordEncoder: BCryptPasswordEncoder, authorityRepository: AuthorityRepository, roleRepository: RoleRepository) : UserDetailsService = UserDetailsServiceImplementation(userService(userRepository, passwordEncoder, authorityRepository, roleRepository))

    @Bean
    fun authenticationProvider(userRepository: UserRepository, authorityRepository: AuthorityRepository, roleRepository: RoleRepository): AuthenticationProvider =
        DaoAuthenticationProvider()
            .also {
                it.setUserDetailsService(userDetailsService(userRepository, passwordEncoder(), authorityRepository, roleRepository))
                it.setPasswordEncoder(passwordEncoder())
            }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager =
        config.authenticationManager
}