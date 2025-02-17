package dev.digiwomb.yas.exception

class EmailExistsException : RuntimeException {
    constructor(): super("An account with this email does already exist")
    constructor(message: String?) : super("An account with this email does already exist: ".plus(message))
    constructor(message: String?, cause: Throwable?) : super("An account with this email does already exist: ".plus(message).plus(cause))
}