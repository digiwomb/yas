package dev.digiwomb.yas.helper

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean
import java.util.Optional

@NoRepositoryBean
interface OwnedEntityRepository<ENTITY : OwnedEntity<ENTITY_ID, USER, USER_ID>, ENTITY_ID, USER: UserAsOwner<USER_ID>, USER_ID> : JpaRepository<ENTITY, ENTITY_ID> {

    fun findById(id: ENTITY_ID): Optional<ENTITY>

    fun findByUserId(userId: USER_ID): List<ENTITY>
}