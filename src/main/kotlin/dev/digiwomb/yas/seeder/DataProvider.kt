package dev.digiwomb.yas.seeder

interface DataProvider<T> {

    fun getData(): List<T>
}