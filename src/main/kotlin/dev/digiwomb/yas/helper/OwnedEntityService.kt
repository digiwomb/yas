package dev.digiwomb.yas.helper

import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
abstract class OwnedEntityService<T : OwnedEntity<ID, OWNER>, ID, OWNER>(
    private val repository: OwnedEntityRepository<T, ID, OWNER>,
    private val ownerService: OwnerService<OWNER>,
) {

    fun findAllEntitiesByUser(user: OWNER): List<T> {

        return if (userHasReadAllAuthority(user)) {
            repository.findAll()
        } else {
            repository.findByOwner(user)
        }
    }

    fun findOwnEntitiesByOwner(owner: OWNER): List<T> = repository.findByOwner(owner)

    abstract fun getReadAllAuthority(): String

    private fun userHasReadAllAuthority(user: OWNER): Boolean {

        return ownerService.findAuthoritiesAsStringByUser(user).contains(getReadAllAuthority())
    }
}