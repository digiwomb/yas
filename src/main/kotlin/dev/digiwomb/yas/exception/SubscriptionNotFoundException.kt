package dev.digiwomb.yas.exception

class SubscriptionNotFoundException : RuntimeException {
    constructor(): super("Subscription not found")
    constructor(message: String?) : super(String.format("Subscription not found: {}", message))
    constructor(message: String?, cause: Throwable?) : super(String.format("Subscription not found: {}", message), cause)
}
