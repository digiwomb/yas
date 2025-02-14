package dev.digiwomb.yas.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.util.JSONPObject
import dev.digiwomb.yas.service.JwtTokenService
import dev.digiwomb.yas.service.UsersDetailsServiceImplementation
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.boot.json.GsonJsonParser
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

        if ((requestURI.startsWith("/api/v") && requestURI.contains("/auth")) || !requestURI.startsWith("/api")) {
            filterChain.doFilter(request, response)
            return
        }

        val authHeader: String? = request.getHeader("Authorization")

        if(authHeader.doesNotContainBearerToken()) {
            val responseBody = mapOf(
                "type" to "about:blank",
                "title" to "Forbidden",
                "status" to 401,
                "detail" to "No authentication token provided",
                "time" to LocalDateTime.now().toString(),
                "path" to request.requestURI
            )

            response.status = HttpServletResponse.SC_FORBIDDEN
            response.contentType = "application/json"
            response.writer.write(ObjectMapper().writeValueAsString(responseBody))
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