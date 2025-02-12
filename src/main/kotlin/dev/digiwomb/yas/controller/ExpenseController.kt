package dev.digiwomb.yas.controller

import dev.digiwomb.yas.model.Expense
import dev.digiwomb.yas.repository.ExpenseRepository
import dev.digiwomb.yas.repository.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/expense")
class ExpenseController() {
    @Autowired
    private lateinit var expenseRepository: ExpenseRepository

    @Autowired
    private lateinit var usersRepository: UsersRepository

    @GetMapping("")
    fun getAll(@AuthenticationPrincipal userDetails: UserDetails) : ResponseEntity<Iterable<Expense>> {
        val user = usersRepository.findByEmail(userDetails.username).orElseThrow()
        val expenses = expenseRepository.findByUser(user)

        return ResponseEntity.ok(expenses)
    }
}