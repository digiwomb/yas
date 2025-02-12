package dev.digiwomb.yas.model

import dev.digiwomb.yas.uuid.UuidV7Generator
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID
import dev.digiwomb.yas.model.mapping.expense.ExpenseTableV001 as ExpenseTable

@Entity
@Table(name = ExpenseTable.TABLE_NAME)
data class Expense(
    @Id
    @GeneratedValue
    @UuidV7Generator
    @Column(name = ExpenseTable.COLUMN_ID)
    val id: UUID? = null,

    @get:NotBlank
    @get:NotNull
    @Column(name = ExpenseTable.COLUMN_TITLE, nullable = false)
    val title: String = "",

    @get:NotNull
    @Column(name = ExpenseTable.COLUMN_AMOUNT, nullable = false)
    val amount: BigDecimal = BigDecimal.ZERO,

    @get:NotNull
    @ManyToOne
    @JoinColumn(name = ExpenseTable.COLUMN_USER_ID, nullable = false)
    val user: Users? = null,

    @Column(name = ExpenseTable.COLUMN_CREATED_AT, nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = ExpenseTable.COLUMN_UPDATED_AT, nullable = false)
    var updatedAt: Instant = Instant.now()
) {
    @PreUpdate
    fun onUpdate() {
        this.copy(updatedAt = Instant.now())
    }
}