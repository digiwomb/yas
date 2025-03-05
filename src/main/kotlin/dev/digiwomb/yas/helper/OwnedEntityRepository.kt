package dev.digiwomb.yas.helper

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface OwnedEntityRepository<T : OwnedEntity<ID, OWNER>, ID, OWNER> : JpaRepository<T, ID> {

    fun findByOwner(owner: OWNER): List<T>
}