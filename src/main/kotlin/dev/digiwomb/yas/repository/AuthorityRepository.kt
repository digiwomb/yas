package dev.digiwomb.yas.repository

import dev.digiwomb.yas.model.Authority
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface AuthorityRepository: JpaRepository<Authority, UUID> {

    fun findByName(name: String): Authority?

    fun findByParent(parent: Authority): List<Authority>
}