package dev.digiwomb.yas.helper

import org.springframework.stereotype.Service

@Service
abstract class OwnedEntityService<ENTITY : OwnedEntity<ENTITY_ID, USER, USER_ID>, ENTITY_ID, USER: UserAsOwner<USER_ID>, USER_ID>(
    private val repository: OwnedEntityRepository<ENTITY, ENTITY_ID, USER, USER_ID>,
    private val userService: UserAsOwnerService<USER, USER_ID>,
) {

    fun findAllEntitiesByUserId(id: USER_ID): List<ENTITY> {

        return if (userHasReadAllAuthority(id)) {
            repository.findAll()
        } else {
            repository.findByUserId(id)
        }
    }

    fun findOwnEntitiesByOwnerId(ownerId: USER_ID): List<ENTITY> = repository.findByUserId(ownerId)

    fun checkWritePermission(entityUserId: USER_ID, requestUserId: USER_ID): Boolean {

        if (entityUserId!!.equals(requestUserId) || userHasWriteAllAuthority(requestUserId)) {
            return true
        } else return false
    }

    abstract fun getReadAllAuthority(): String

    abstract fun getWriteAllAuthority(): String

    private fun userHasReadAllAuthority(userId: USER_ID): Boolean {

        return userService.findAuthoritiesAsStringByUserId(userId).contains(getReadAllAuthority())
    }

    private fun userHasWriteAllAuthority(userId: USER_ID): Boolean {

        return userService.findAuthoritiesAsStringByUserId(userId).contains(getWriteAllAuthority())
    }
}