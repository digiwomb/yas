package dev.digiwomb.yas.service

import dev.digiwomb.yas.repository.RoleRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service

@Service
class RoleService(
    private val roleRepository: RoleRepository
) {

    fun findByNameWithAuthorities(name: String) = roleRepository.findByNameWithAuthorities(name)
        ?: throw NotFoundException()
}