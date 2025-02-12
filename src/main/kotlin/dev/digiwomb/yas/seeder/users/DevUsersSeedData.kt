package dev.digiwomb.yas.seeder.users

import dev.digiwomb.yas.model.Users
import dev.digiwomb.yas.seeder.DataProvider
import net.datafaker.Faker
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
@Profile("dev")
class DevUsersSeedData : DataProvider<Users> {
    private val users = mutableListOf<Users>()
    private val faker = Faker()
    private val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()

    init {
        val admin = Users(
            email = "admin@yas.local",
            name = faker.name().fullName(),
            password = passwordEncoder.encode("password123")
        )

        users.add(admin)

        repeat(4) {
            val user = Users(
                email = faker.internet().emailAddress(),
                name = faker.name().fullName(),
                password = passwordEncoder.encode("password123")
            )
            users.add(user)
        }
    }

    override fun getData(): List<Users> {
        return users
    }

    override fun getSortingNumber(): Int {
        return 0
    }
}