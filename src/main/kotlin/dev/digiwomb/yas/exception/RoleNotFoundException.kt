package dev.digiwomb.yas.exception

class RoleNotFoundException: RuntimeException {
    constructor(): super("Role not found")
    constructor(message: String?) : super(String.format("Role not found: {}", message))
    constructor(message: String?, cause: Throwable?) : super(String.format("Role not found: {}", message), cause)
}