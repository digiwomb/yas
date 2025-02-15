package dev.digiwomb.yas.controller.subscription

import dev.digiwomb.yas.exception.SubscriptionNotFoundException
import dev.digiwomb.yas.model.Subscription
import dev.digiwomb.yas.repository.SubscriptionRepository
import dev.digiwomb.yas.repository.UserRepository
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/subscription")
class SubscriptionController() {
    @Autowired
    private lateinit var subscriptionRepository: SubscriptionRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @GetMapping("")
    fun getAll(@AuthenticationPrincipal userDetails: UserDetails) : ResponseEntity<Iterable<Subscription>> {
        val user = userRepository.findByEmail(userDetails.username).orElseThrow()
        val subscriptions = subscriptionRepository.findByUser(user)

        return ResponseEntity.ok(subscriptions)
    }

    @GetMapping("/{id}")
    fun getSubscriptionById(@PathVariable id: UUID, @AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<Subscription> {
        val user = userRepository.findByEmail(userDetails.username).orElseThrow()
        val subscription = subscriptionRepository.findById(id)
            .filter { it.user == user }
            .orElseThrow { SubscriptionNotFoundException() }
        return ResponseEntity.ok(subscription)
    }

    @PostMapping("")
    fun createSubscription(
        @Valid @RequestBody request: CreateSubscriptionRequest,
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<Subscription> {
        val user = userRepository.findByEmail(userDetails.username).orElseThrow()
        val newSubscription = Subscription(
            title = request.title,
            amount = request.amount,
            user = user
        )
        return ResponseEntity.ok(subscriptionRepository.save(newSubscription))
    }
}