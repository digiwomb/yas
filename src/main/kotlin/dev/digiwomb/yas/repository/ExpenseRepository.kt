package dev.digiwomb.yas.repository

import dev.digiwomb.yas.model.Expense
import dev.digiwomb.yas.model.Users
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ExpenseRepository : JpaRepository<Expense, UUID> {

    fun findByUser(user: Users): List<Expense>
}
