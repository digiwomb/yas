package dev.digiwomb.yas.controller.subscription

import dev.digiwomb.yas.model.Subscription
import dev.digiwomb.yas.repository.SubscriptionRepository
import dev.digiwomb.yas.repository.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/subscription")
class SubscriptionController() {
    @Autowired
    private lateinit var subscriptionRepository: SubscriptionRepository

    @Autowired
    private lateinit var usersRepository: UsersRepository

    @GetMapping("")
    fun getAll(@AuthenticationPrincipal userDetails: UserDetails) : ResponseEntity<Iterable<Subscription>> {
        val user = usersRepository.findByEmail(userDetails.username).orElseThrow()
        val subscriptions = subscriptionRepository.findByUser(user)

        return ResponseEntity.ok(subscriptions)
    }
}