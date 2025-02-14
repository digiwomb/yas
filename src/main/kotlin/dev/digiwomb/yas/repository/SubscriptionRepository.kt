package dev.digiwomb.yas.repository

import dev.digiwomb.yas.model.Subscription
import dev.digiwomb.yas.model.Users
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface SubscriptionRepository : JpaRepository<Subscription, UUID> {

    fun findByUser(user: Users): List<Subscription>
}
