package dev.digiwomb.yas.repository

import dev.digiwomb.yas.model.Subscription
import dev.digiwomb.yas.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface SubscriptionRepository : JpaRepository<Subscription, UUID> {

    fun findByUser(user: User): List<Subscription>

    override fun findById(id: UUID): Optional<Subscription>

    fun findByIdAndUser(id: UUID, user: User): Subscription?
}
