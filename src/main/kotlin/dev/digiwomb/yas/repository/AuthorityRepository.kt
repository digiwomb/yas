package dev.digiwomb.yas.repository

import dev.digiwomb.yas.model.Authority
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface AuthorityRepository: JpaRepository<Authority, UUID> {

    fun findByName(name: String): Authority?

    fun findByParent(parent: Authority): List<Authority>

    @Query("select a from Authority a left join fetch a.children where a.name = :name")
    fun findByNameWithChildren(@Param("name") name: String): Authority?
}