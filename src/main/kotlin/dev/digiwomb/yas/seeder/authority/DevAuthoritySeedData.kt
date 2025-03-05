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

        val subscriptionReadAllAuthority = Authority(
            name = "SUBSCRIPTION_READ_ALL"
        )

        authorities.add(subscriptionReadAllAuthority)

        val subscriptionReadAuthority = Authority(
            name = "SUBSCRIPTION_READ",
            parent = subscriptionReadAllAuthority
        )

        authorities.add(subscriptionReadAuthority)

        return authorities
    }
}