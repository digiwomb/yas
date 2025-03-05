package dev.digiwomb.yas.repository

import dev.digiwomb.yas.model.Authority
import dev.digiwomb.yas.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface RoleRepository : JpaRepository<Role, UUID> {

    fun findByName(name: String): Role?
}