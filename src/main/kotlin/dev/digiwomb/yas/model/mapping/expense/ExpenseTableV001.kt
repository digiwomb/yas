package dev.digiwomb.yas.model.mapping.expense

open class ExpenseTableV001 {
    companion object {
        const val TABLE_NAME = "expense"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_AMOUNT = "amount"
        const val COLUMN_USER_ID = "user_id"
        const val COLUMN_CREATED_AT = "created_at"
        const val COLUMN_UPDATED_AT = "updated_at"
    }
}