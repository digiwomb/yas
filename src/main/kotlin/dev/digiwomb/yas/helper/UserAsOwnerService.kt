package dev.digiwomb.yas.helper

interface UserAsOwnerService<USER: UserAsOwner<ID>, ID> {

    fun findById(id: ID): USER
    fun findAuthoritiesAsStringByUserId(ownerID: ID): List<String>
    fun findByEmail(username: String): USER
}