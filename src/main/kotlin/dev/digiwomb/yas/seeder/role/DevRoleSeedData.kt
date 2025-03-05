package dev.digiwomb.yas.seeder.role

import dev.digiwomb.yas.model.Role
import dev.digiwomb.yas.repository.AuthorityRepository
import dev.digiwomb.yas.seeder.DataProvider
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("dev")
class DevRoleSeedData(
    private val authorityRepository: AuthorityRepository
) : DataProvider<Role> {
    override fun getSortingNumber(): Int {
        return 200
    }

    override fun getData(): List<Role> {

        val authorities = authorityRepository.findAll().associateBy { it.name }
        val roles = mutableListOf<Role>()

        val userRole = Role(
            name = "ROLE_USER",
            authorities = mutableListOf(authorities["SUBSCRIPTION_READ"]!!)
        )
        roles.add(userRole)

        val adminRole = Role(
            name = "ROLE_ADMIN",
            authorities = mutableListOf(authorities["SUBSCRIPTION_READ_ALL"]!!)
        )
        roles.add(adminRole)

        return roles
    }
}