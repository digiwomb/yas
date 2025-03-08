package dev.digiwomb.yas.service

import dev.digiwomb.yas.controller.subscription.SubscriptionResponse
import dev.digiwomb.yas.exception.SubscriptionNotFoundException
import dev.digiwomb.yas.model.Subscription
import dev.digiwomb.yas.model.User
import dev.digiwomb.yas.repository.SubscriptionRepository
import org.springframework.stereotype.Service
import java.util.UUID
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

@Service
class SubscriptionService(
    private val subscriptionRepository: SubscriptionRepository
) {

    fun findByUser(user: User): List<Subscription> = subscriptionRepository.findByUser(user)

    fun findByIdAndUser(id: UUID, user: User): Subscription {
        val subscription =  subscriptionRepository.findByIdAndUser(id, user)
            ?: throw SubscriptionNotFoundException()

        return subscription
    }

    fun create(subscription: Subscription): Subscription {

        subscriptionRepository.save(subscription)
        return subscription
    }

    fun findById(id: UUID): Subscription {
        val subscription = subscriptionRepository.findById(id).orElseThrow { SubscriptionNotFoundException() }

        return subscription
    }

    fun deleteByIdAndUser(id: UUID, user: User) {

        subscriptionRepository.delete(findByIdAndUser(id, user))
    }

    fun update(id: UUID, subscription: Subscription, user: User): Subscription {

        findByIdAndUser(id, user)
        val updatedSubscription = subscription.copy(
            id = id,
            user = user
        )

        subscriptionRepository.save(updatedSubscription)

        return updatedSubscription
    }
}