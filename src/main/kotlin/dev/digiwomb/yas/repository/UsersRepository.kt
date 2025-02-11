package dev.digiwomb.yas.repository

import dev.digiwomb.yas.model.Users
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UsersRepository : JpaRepository<Users, UUID> {
    fun findByEmail(email: String): Optional<Users>
}
