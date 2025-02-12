package dev.digiwomb.yas.seeder.migration

import dev.digiwomb.yas.model.Expense
import dev.digiwomb.yas.repository.UsersRepository
import dev.digiwomb.yas.seeder.DataProvider
import net.datafaker.Faker
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.math.BigDecimal
import kotlin.random.Random

@Component
@Profile("dev")
class DevExpenseSeedData(
    private val usersRepository: UsersRepository
) : DataProvider<Expense> {
    private val expenses = mutableListOf<Expense>()
    private val faker = Faker()

    @Bean
    override fun getData(): List<Expense> {
        usersRepository.findAll().forEach { users ->
            repeat(5) {
                val expense = Expense(
                    title = faker.hobby().activity(),
                    amount = BigDecimal(Random.nextDouble(10.0, 500.0)).setScale(2, BigDecimal.ROUND_HALF_UP),
                    userId = users
                )
                expenses.add(expense)
            }
        }
    return expenses
    }

    override fun getSortingNumber(): Int {
        return 1
    }
}