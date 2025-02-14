package dev.digiwomb.yas.config

import dev.digiwomb.yas.service.JwtTokenService
import dev.digiwomb.yas.service.UsersDetailsServiceImplementation
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val usersDetailsService: UsersDetailsServiceImplementation,
    private val tokenService: JwtTokenService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val requestURI = request.requestURI

        if (requestURI.startsWith("/api/v") && requestURI.contains("/auth")) {
            filterChain.doFilter(request, response)
            return
        }

        val authHeader: String? = request.getHeader("Authorization")

        if(authHeader.doesNotContainBearerToken()) {
            response.status = HttpServletResponse.SC_FORBIDDEN
            response.contentType = "application/json"
            response.writer.write("{\"error\": \"Access Denied: No Authorization Header\"}")
            return
        }

        val jwtToken = authHeader!!.extractTokenValue()
        val email = tokenService.extractEmail(jwtToken)
        
        if(email != null && SecurityContextHolder.getContext().authentication == null) {
            val foundUser = usersDetailsService.loadUserByUsername(email)
            
            if(tokenService.isValid(jwtToken, foundUser)) {
                updateContext(foundUser, request)
            }

            filterChain.doFilter(request, response)
        }
    }

    private fun updateContext(foundUser: UserDetails, request: HttpServletRequest) {
        val authToken = UsernamePasswordAuthenticationToken(foundUser, null, foundUser.authorities)
        authToken.details = WebAuthenticationDetailsSource().buildDetails(request)

        SecurityContextHolder.getContext().authentication = authToken
    }

    private fun String?.doesNotContainBearerToken(): Boolean =
        this == null || !this.startsWith("Bearer")

    private fun String.extractTokenValue() : String =
        this.substringAfter("Bearer ")
}