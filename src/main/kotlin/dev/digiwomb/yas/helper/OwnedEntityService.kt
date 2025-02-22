package dev.digiwomb.yas.helper

import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
abstract class OwnedEntityService<T : OwnedEntity<ID, OWNER>, ID, OWNER>(
    private val repository: OwnedEntityRepository<T, ID, OWNER>,
    private val ownerService: OwnerService<OWNER>,
    private val userDetailsService: UserDetailsService
) {

    fun getEntitiesForUser(authentication: Authentication): List<T> {
        val owner = ownerService.findByUsername(authentication.name)

        return if (userHasReadAllAuthority(owner)) {
            repository.findAll()
        } else {
            repository.findByOwner(owner)
        }
    }

    abstract fun getReadAllAuthority(): String

    private fun userHasReadAllAuthority(userName: String): Boolean {

        val userDetails = userDetailsService.loadUserByUsername(userName)
        userDetails.authorities.forEach {  }
        return (owner as? User)?.authorities?.contains(getReadAllAuthority()) ?: false
    }
}