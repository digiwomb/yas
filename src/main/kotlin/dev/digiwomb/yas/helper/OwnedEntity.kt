package dev.digiwomb.yas.helper

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@MappedSuperclass
abstract class OwnedEntity<ENTITY_ID, USER: UserAsOwner<USER_ID>, USER_ID>(

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false, unique = true)
    val id: ENTITY_ID? = null,

    @ManyToOne
    @get:NotNull
    @JoinColumn(updatable = false, nullable = false)
    val user: USER? = null
) {}