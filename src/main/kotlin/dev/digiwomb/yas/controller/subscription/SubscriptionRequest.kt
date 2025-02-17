package dev.digiwomb.yas.controller.subscription

import dev.digiwomb.yas.model.Subscription
import dev.digiwomb.yas.model.User
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class SubscriptionRequest(

    @field:NotBlank(message = "Title must not be blank")
    val title: String,

    @field:NotNull(message = "Amount must not be null")
    val amount: BigDecimal
) {

    internal fun toModel(user: User) : Subscription =
        Subscription(
            title = this.title,
            amount = this.amount,
            user = user
        )
}
