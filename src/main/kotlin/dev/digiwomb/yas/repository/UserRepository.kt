package dev.digiwomb.yas.repository

import dev.digiwomb.yas.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, UUID> {

    fun findByEmail(email: String): User?
}
