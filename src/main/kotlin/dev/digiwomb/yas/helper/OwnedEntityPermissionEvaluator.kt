package dev.digiwomb.yas.helper

import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.io.Serializable

@Component
class OwnedEntityPermissionEvaluator<ENTITY : OwnedEntity<ENTITY_ID, USER, USER_ID>, ENTITY_ID, USER: UserAsOwner<USER_ID>, USER_ID>(
    private val ownedEntityService: OwnedEntityService<ENTITY, ENTITY_ID, USER, USER_ID>,
    private val userAsOwnerService: UserAsOwnerService<USER, USER_ID>
): PermissionEvaluator {

    override fun hasPermission(authentication: Authentication , targetDomainObject: Any, permission: Any): Boolean {

        return true
    }

    override fun hasPermission(
        authentication: Authentication,
        targetId: Serializable,
        targetType: String,
        permission: Any
    ): Boolean {

        val operation = Operation.findByName(permission.toString().uppercase())
            ?: throw IllegalArgumentException("Operation $permission not found")
        val clazz = targetType.uppercase()
        //val authorities = mutableListOf<GrantedAuthority>().addAll(authentication!!.authorities)
        val requestUserId = userAsOwnerService.findByEmail(authentication.name).id!!
        val entityId: ENTITY_ID = targetId as ENTITY_ID

        return when (clazz) {
            "SUBSCRIPTION" ->
                if (Operation.isChildrenOf(Operation.READ, operation)) return ownedEntityService.checkIfOwnEntityOrHasReadAllPermission(entityId, requestUserId)
                else return ownedEntityService.checkIfOwnEntityOrHasWriteAllPermission(entityId, requestUserId)
            else -> false
        }
    }
}