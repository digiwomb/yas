package dev.digiwomb.yas.exception

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@ControllerAdvice
class ResponseEntityExceptionHandlerImplementation : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [SubscriptionNotFoundException::class])
    protected fun handleNotFound(
        ex: RuntimeException, request: WebRequest?
    ): ResponseEntity<Any>? {
        val response: Map<String, Any> = mapOf(
            "title" to "There is an error",
            "status" to HttpStatus.NOT_FOUND.value(),
            "time" to LocalDateTime.now().
            format(DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm:ss")),
            "errors" to mapOf(
                "msg" to ex.message
            ),
        )
        return handleExceptionInternal(
            ex, response,
            HttpHeaders(), HttpStatus.NOT_FOUND, request!!
        )
    }
}