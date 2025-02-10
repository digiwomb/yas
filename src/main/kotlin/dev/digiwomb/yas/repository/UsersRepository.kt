package dev.digiwomb.yas.repository

import dev.digiwomb.yas.model.Users
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UsersRepository : JpaRepository<Users, Long> {
    fun findByEmail(email: String): Optional<Users>
}
