package dev.digiwomb.yas.seeder.user

import dev.digiwomb.yas.model.User
import dev.digiwomb.yas.seeder.DataProvider
import net.datafaker.Faker
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
@Profile("dev")
class DevUserSeedData : DataProvider<User> {
    private val users = mutableListOf<User>()
    private val faker = Faker()
    private val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()

    init {
        val admin = User(
            email = "admin@yas.local",
            name = faker.name().fullName(),
            password = passwordEncoder.encode("password123")
        )

        users.add(admin)

        repeat(4) {
            val user = User(
                email = faker.internet().emailAddress(),
                name = faker.name().fullName(),
                password = passwordEncoder.encode("password123")
            )
            users.add(user)
        }
    }

    override fun getData(): List<User> {
        return users
    }

    override fun getSortingNumber(): Int {
        return 0
    }
}