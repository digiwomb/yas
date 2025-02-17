package dev.digiwomb.yas.config

import com.fasterxml.jackson.databind.ObjectMapper
import dev.digiwomb.yas.service.JwtTokenService
import dev.digiwomb.yas.service.UserDetailsServiceImplementation
import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.time.LocalDateTime

@Component
class JwtAuthenticationFilter(
    private val userDetailsService: UserDetailsServiceImplementation,
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

            handleInvalidOrExpiredTokenException(response, request.requestURI, "No authentication token provided")
            return
        }

        val jwtToken = authHeader!!.extractTokenValue()

        try {
            val email = tokenService.extractEmail(jwtToken)

            if(email != null && SecurityContextHolder.getContext().authentication == null) {
                val foundUser = userDetailsService.loadUserByUsername(email)

                if(tokenService.isValid(jwtToken, foundUser)) {
                    updateContext(foundUser, request)
                }

                filterChain.doFilter(request, response)
            }
        } catch (ex: Exception) {
            handleInvalidOrExpiredTokenException(response, request.requestURI, ex.message.orEmpty())
            return
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

    private fun handleInvalidOrExpiredTokenException(response: HttpServletResponse, path: String, detail: String) {
        val status = HttpStatus.UNAUTHORIZED
        val responseBody = mapOf(
            "type" to "about:blank",
            "title" to status.reasonPhrase,
            "status" to status.value(),
            "detail" to detail,
            "time" to LocalDateTime.now().toString(),
            "path" to path
        )

        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = "application/json"
        response.writer.write(ObjectMapper().writeValueAsString(responseBody))
    }
}