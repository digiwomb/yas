package dev.digiwomb.yas.helper

interface OwnerService<U> {
    fun findUsernameByUser(user: U): String
    fun findByUsername(username: String): U
    fun findAuthoritiesAsStringByUser(user: U): List<String>
}