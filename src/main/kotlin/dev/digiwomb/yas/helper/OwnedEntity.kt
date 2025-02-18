package dev.digiwomb.yas.helper

import dev.digiwomb.yas.helper.annotation.uuid.UuidV7Generator
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@MappedSuperclass
abstract class OwnedEntity<ID, OWNER>(

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false, unique = true)
    val id: ID? = null,

    @ManyToOne
    @get:NotNull
    @JoinColumn(updatable = false, nullable = false)
    val user: OWNER? = null
) {}