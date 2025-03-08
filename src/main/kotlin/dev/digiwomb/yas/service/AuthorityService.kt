package dev.digiwomb.yas.service

import dev.digiwomb.yas.model.Authority
import dev.digiwomb.yas.repository.AuthorityRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service

@Service
class AuthorityService(
    private val authorityRepository: AuthorityRepository,
) {

    fun getAllChildren(authority: Authority): List<Authority> {

        val allChildren = mutableListOf<Authority>()
        val authorityWithChildren = authorityRepository.findByNameWithChildren(authority.name)
            ?: throw NotFoundException()

        authorityWithChildren.children.forEach { children ->
            allChildren.add(children)
            allChildren.addAll(getAllChildren(children))
        }

        return allChildren
    }
}