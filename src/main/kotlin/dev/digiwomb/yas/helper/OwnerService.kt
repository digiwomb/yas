package dev.digiwomb.yas.helper

interface OwnerService<U> {
    fun findByUsername(username: String): U
}