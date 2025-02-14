package dev.digiwomb.yas.service

import dev.digiwomb.yas.config.JwtProperties
import dev.digiwomb.yas.controller.auth.AuthenticationRequest
import dev.digiwomb.yas.controller.auth.AuthenticationResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationService(
    private val authManager: AuthenticationManager,
    private val usersDetailsService: UsersDetailsServiceImplementation,
    private val jwtTokenService: JwtTokenService,
    private val jwtProperties: JwtProperties
) {
    fun authentication(authRequest: AuthenticationRequest): AuthenticationResponse {
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authRequest.email,
                authRequest.password
            )
        )

        val user = usersDetailsService.loadUserByUsername(authRequest.email)
        val accessToken = jwtTokenService.generate(
            userDetails = user,
            expirationDate = Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)
        )

        return AuthenticationResponse(
            accessToken = accessToken
        )
    }

}
