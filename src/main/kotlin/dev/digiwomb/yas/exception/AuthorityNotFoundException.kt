package dev.digiwomb.yas.exception

class AuthorityNotFoundException: RuntimeException {
    constructor(): super("Authority not found")
    constructor(message: String?) : super(String.format("Authority not found: {}", message))
    constructor(message: String?, cause: Throwable?) : super(String.format("Authority not found: {}", message), cause)
}