package dev.digiwomb.yas.exception

class InvalidOldPasswordException: RuntimeException {
    constructor(): super("Old password does not match")
}