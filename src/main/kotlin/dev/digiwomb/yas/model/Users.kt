package dev.digiwomb.yas.model

import jakarta.persistence.*

@Entity
@Table(name = "users") // Umbenennung von 'user' zu 'users', um SQL-Fehler zu vermeiden
data class Users(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    val email: String = "",

    @Column(nullable = false)
    val name: String = "",

    @Column(nullable = false)
    val password: String = ""
) {

    constructor() : this(0, "", "", "")
}
