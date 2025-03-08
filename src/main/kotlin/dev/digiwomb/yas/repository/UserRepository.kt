package dev.digiwomb.yas.repository

import dev.digiwomb.yas.model.User
import dev.digiwomb.yas.model.dto.UserDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface UserRepository : JpaRepository<User, UUID> {

    fun findByEmail(email: String): User?

//    @Query("""
//        select new dev.digiwomb.yas.model.dto.UserDto(
//            u.id as id,
//            u.email as email,
//            u.name as name,
//            u.password as password,
//            u.createdAt as createdAt,
//            u.updatedAt as updatedAt
//        )
//        from
//            User u
//        where
//            u.email = :email
//    """)
//    fun findByEmailAsDto(@Param("email") email: String): UserDto?

    @Query("select distinct u from User u left join fetch u.roles where u.email = :email")
    fun findByEmailWithRoles(@Param("email") email: String): User?
}
