package dev.digiwomb.yas.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "spring.jwt")
class JwtProperties {
    lateinit var key: String
    var accessTokenExpiration: Long = 0
    var refreshTokenExpiration: Long = 0
}