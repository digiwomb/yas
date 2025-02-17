package dev.digiwomb.yas.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ControllerAdvice
import java.time.LocalDateTime
import dev.digiwomb.yas.controller.ErrorResponse
import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.converter.HttpMessageNotReadableException

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(
        ex: MethodArgumentNotValidException,
        request: HttpServletRequest,
    ): ResponseEntity<ErrorResponse> {
        val errorMap = ex.bindingResult.fieldErrors.associate {
            it.field to (it.defaultMessage ?: "Invalid field")
        }

        val errorResponse = ErrorResponse(
            timestamp = LocalDateTime.now(),
            status = HttpStatus.BAD_REQUEST.value(),
            errors = errorMap,
            error = "Validation Error",
            path = request.requestURI,
            detail = null,
            type = "about:blank"
        )

        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }
    
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpNotReadableException(
        ex: HttpMessageNotReadableException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {

        val errorResponse = ErrorResponse(
            type = "about:blank",
            timestamp = LocalDateTime.now(),
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            status = HttpStatus.BAD_REQUEST.value(),
            errors = null,
            detail = ex.message,
            path = request.requestURI
        )

        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [SubscriptionNotFoundException::class])
    protected fun handleSubscriptionNotFoundException(
        ex: RuntimeException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {

        val errorResponse = ErrorResponse(
            type = "about:blank",
            timestamp = LocalDateTime.now(),
            error = HttpStatus.NOT_FOUND.reasonPhrase,
            status = HttpStatus.NOT_FOUND.value(),
            errors = null,
            detail = ex.message,
            path = request.requestURI
        )

        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(value = [ExpiredJwtException::class])
    protected fun handleExpiredJwtException(
        ex: RuntimeException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {

        val status = HttpStatus.UNAUTHORIZED
        val errorResponse = ErrorResponse(
            type = "about:blank",
            timestamp = LocalDateTime.now(),
            error = status.reasonPhrase,
            status = status.value(),
            errors = null,
            detail = ex.message,
            path = request.requestURI
        )
        return ResponseEntity(errorResponse, status)
    }
}
