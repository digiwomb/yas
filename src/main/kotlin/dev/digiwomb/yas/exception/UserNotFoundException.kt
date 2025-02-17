package dev.digiwomb.yas.exception

class UserNotFoundException : RuntimeException {
    constructor(): super("User not found")
    constructor(message: String?) : super(String.format("User not found: {}", message))
    constructor(message: String?, cause: Throwable?) : super(String.format("User not found: {}", message), cause)
}