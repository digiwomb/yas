package dev.digiwomb.yas.security.dev

import dev.digiwomb.yas.security.UsersDetailsServiceImplementation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@Profile("dev")
class SecurityConfigDev(private val usersDetailsService: UsersDetailsServiceImplementation) {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationManager(): AuthenticationManager {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(usersDetailsService)
        authProvider.setPasswordEncoder(passwordEncoder())
        return ProviderManager(authProvider)
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests {auth ->
                auth.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                auth.anyRequest().authenticated()
            }
            .formLogin(Customizer.withDefaults())
            .csrf { it.disable() }
            .headers { it.frameOptions { frame -> frame.disable() } }

        return http.build()
    }
}
