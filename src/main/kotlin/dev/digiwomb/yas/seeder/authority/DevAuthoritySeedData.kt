package dev.digiwomb.yas.seeder.authority

import dev.digiwomb.yas.model.Authority
import dev.digiwomb.yas.seeder.DataProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("dev")
class DevAuthoritySeedData : DataProvider<Authority> {
    private val authorities = mutableListOf<Authority>()

    override fun getSortingNumber(): Int {
        return 100
    }

    override fun getData(): List<Authority> {

        val entities = arrayOf("SUBSCRIPTION")

        authorities.addAll(generateAuthorities(entities))

        return authorities
    }

    private fun generateAuthorities(strings: Array<String>): List<Authority> {
        val authorities = mutableListOf<Authority>()

        for (str in strings) {
            val readAll = Authority(name= "${str}_READ_ALL")
            val writeAll = Authority(name= "${str}_WRITE_ALL")
            val create = Authority(name= "${str}_CREATE", parent = writeAll)
            val read = Authority(name= "${str}_READ", parent = readAll)
            val update = Authority(name= "${str}_UPDATE", parent = writeAll)
            val delete = Authority(name= "${str}_DELETE", parent = writeAll)

            authorities.addAll(listOf(readAll, writeAll, create, read, update, delete))
        }

        return authorities
    }
}