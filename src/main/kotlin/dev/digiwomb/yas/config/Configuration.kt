package dev.digiwomb.yas.config

import dev.digiwomb.yas.repository.AuthorityRepository
import dev.digiwomb.yas.repository.UserRepository
import dev.digiwomb.yas.service.AuthorityService
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
    fun userService(userRepository: UserRepository, passwordEncoder: BCryptPasswordEncoder, authorityRepository: AuthorityRepository) : UserService = UserService(userRepository, passwordEncoder, authorityService(authorityRepository))

    @Bean
    fun userDetailsService(userRepository: UserRepository, passwordEncoder: BCryptPasswordEncoder, authorityRepository: AuthorityRepository) : UserDetailsService = UserDetailsServiceImplementation(userService(userRepository, passwordEncoder, authorityRepository))

    @Bean
    fun authenticationProvider(userRepository: UserRepository, authorityRepository: AuthorityRepository): AuthenticationProvider =
        DaoAuthenticationProvider()
            .also {
                it.setUserDetailsService(userDetailsService(userRepository, passwordEncoder(), authorityRepository))
                it.setPasswordEncoder(passwordEncoder())
            }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager =
        config.authenticationManager
}