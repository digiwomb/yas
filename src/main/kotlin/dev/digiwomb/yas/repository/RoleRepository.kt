package dev.digiwomb.yas.repository

import dev.digiwomb.yas.model.Authority
import dev.digiwomb.yas.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface RoleRepository : JpaRepository<Role, UUID> {

    fun findByName(name: String): Role?

    @Query("select distinct r from Role r left join fetch r.authorities where r.name = :name")
    fun findByNameWithAuthorities(@Param("name") name: String): Role?
}