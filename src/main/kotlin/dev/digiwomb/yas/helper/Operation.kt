package dev.digiwomb.yas.helper

enum class Operation(val parent: Operation?) {

    READ_ALL(null),
    WRITE_ALL(null),
    READ(READ_ALL),
    WRITE(WRITE_ALL),
    CREATE(WRITE),
    UPDATE(WRITE),
    DELETE(WRITE);

    companion object {

        fun findByName(name: String): Operation? = entries.find { it.name == name }

        fun isChildrenOf(parent: Operation, children: Operation): Boolean = findWithChildren(parent).contains(children)

        private fun findWithChildren(operation: Operation): MutableList<Operation> {

            val result = mutableListOf<Operation>(operation)
            val childrenFirstOrder = entries.filter { it.parent == operation }

            childrenFirstOrder.forEach{ children ->
                result.addAll(findWithChildren(children))
            }

            return result
        }
    }
}