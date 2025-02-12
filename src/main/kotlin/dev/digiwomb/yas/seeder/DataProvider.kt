package dev.digiwomb.yas.seeder

interface DataProvider<T> {

    fun getSortingNumber(): Int
    fun getData(): List<T>
}