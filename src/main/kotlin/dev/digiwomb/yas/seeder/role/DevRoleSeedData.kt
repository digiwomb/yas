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

        val roles = mutableListOf<Role>()
        val entities = arrayOf("SUBSCRIPTION")


        val userRole = Role(
            name = "ROLE_USER",
            authorities = authorityRepository.findAll().filter { authority ->
                entities.any { entity -> authority.name.contains(entity) && !authority.name.endsWith("_ALL") }
            }.toMutableList()
        )
        roles.add(userRole)

        val adminRole = Role(
            name = "ROLE_ADMIN",
//            authorities = authorityRepository.findAll()
            authorities = authorityRepository.findAll().filter { authority ->
                entities.any { entity -> authority.name.contains(entity) && authority.name.endsWith("_ALL") }
            }.toMutableList()
        )
        roles.add(adminRole)

        return roles
    }
}