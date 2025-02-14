package dev.digiwomb.yas.seeder.subscription

import dev.digiwomb.yas.model.Subscription
import dev.digiwomb.yas.repository.UsersRepository
import dev.digiwomb.yas.seeder.DataProvider
import net.datafaker.Faker
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.random.Random

@Component
@Profile("dev")
class DevSubscriptionSeedData(
    private val usersRepository: UsersRepository
) : DataProvider<Subscription> {
    private val subscriptions = mutableListOf<Subscription>()
    private val faker = Faker()

    @Bean
    override fun getData(): List<Subscription> {
        usersRepository.findAll().forEach { users ->
            repeat(5) {
                val subscription = Subscription(
                    title = faker.hobby().activity(),
                    amount = BigDecimal(Random.nextDouble(10.0, 500.0)).setScale(2, RoundingMode.HALF_UP),
                    user = users
                )
                subscriptions.add(subscription)
            }
        }
    return subscriptions
    }

    override fun getSortingNumber(): Int {
        return 1
    }
}