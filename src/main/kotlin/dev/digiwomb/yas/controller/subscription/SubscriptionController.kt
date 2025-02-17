package dev.digiwomb.yas.controller.subscription

import dev.digiwomb.yas.controller.user.UserController
import dev.digiwomb.yas.controller.user.UserResponse
import dev.digiwomb.yas.exception.SubscriptionNotFoundException
import dev.digiwomb.yas.model.Subscription
import dev.digiwomb.yas.model.User
import dev.digiwomb.yas.service.SubscriptionService
import dev.digiwomb.yas.service.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/subscription")
class SubscriptionController(
    private val userService: UserService,
    private val subscriptionService: SubscriptionService,
) {

    @GetMapping("")
    fun findAll(@AuthenticationPrincipal userDetails: UserDetails) : List<SubscriptionResponse> {

        val user = userService.findByEmail(userDetails.username)

        val response = subscriptionService.findByUser(user).map {
            it.toResponse(user)
        }

        return response
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: UUID, @AuthenticationPrincipal userDetails: UserDetails): SubscriptionResponse {

        val user = userService.findByEmail(userDetails.username)

        return subscriptionService.findByIdAndUser(id, user).toResponse(user)
    }

    @PostMapping("")
    fun create(
        @Valid @RequestBody request: SubscriptionRequest,
        @AuthenticationPrincipal userDetails: UserDetails
    ): SubscriptionResponse {

        val user = userService.findByEmail(userDetails.username)

        val response = subscriptionService.create(request.toModel(user))
            .toResponse(user)

        return response
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: UUID,
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<Void> {

        val user = userService.findByEmail(userDetails.username)

        subscriptionService.deleteByIdAndUser(id, user)

        return ResponseEntity.noContent().build()
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: UUID,
        @Valid @RequestBody request: SubscriptionRequest,
        @AuthenticationPrincipal userDetails: UserDetails
    ): SubscriptionResponse {

        val user = userService.findByEmail(userDetails.username)
        return subscriptionService.update(id, request.toModel(user), user).toResponse(user)
    }
}