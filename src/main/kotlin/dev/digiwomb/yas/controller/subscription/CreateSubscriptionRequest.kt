package dev.digiwomb.yas.controller.subscription

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class CreateSubscriptionRequest(

    @field:NotBlank(message = "Title must not be blank")
    val title: String,

    @field:NotNull(message = "Amount must not be null")
    val amount: BigDecimal
)
