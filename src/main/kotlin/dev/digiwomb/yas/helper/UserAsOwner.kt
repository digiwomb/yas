package dev.digiwomb.yas.helper

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass

@MappedSuperclass
abstract class UserAsOwner<ID>(

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false, unique = true)
    val id: ID? = null,

    val email: String? = null
) {}