package dev.digiwomb.yas.config.dev

import dev.digiwomb.yas.config.JwtAuthenticationFilter
import dev.digiwomb.yas.helper.OwnedEntityPermissionEvaluator
import dev.digiwomb.yas.model.Subscription
import dev.digiwomb.yas.model.User
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.annotation.Order
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import java.util.UUID


@Configuration
@Profile("dev")
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfigDev(
    private val authenticationProvider: AuthenticationProvider,
) {

    @Bean
    @Order(1)
    fun apiSecurityFilterChain(
        http: HttpSecurity,
        jwtAuthenticationFilter: JwtAuthenticationFilter
    ): SecurityFilterChain {
        http
            .securityMatcher("/api/**")
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers(AntPathRequestMatcher("/api/v*/auth"))
                    .permitAll()
                    .anyRequest()
                    .fullyAuthenticated()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    @Order(2)
    fun formLoginSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .securityMatcher("/**")
            .csrf { it.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")) }
            .authorizeHttpRequests {
                it
                    .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                    .requestMatchers("/error").permitAll()
                    .anyRequest().authenticated()
            }
            .formLogin(Customizer.withDefaults())
            .headers { it.frameOptions { frame -> frame.disable() } }

        return http.build()
    }

    @Bean
    fun expressionHandler(
        permissionEvaluator: OwnedEntityPermissionEvaluator<Subscription, UUID, User, UUID>?
    ): MethodSecurityExpressionHandler {
        // create a new DefaultMethodSecurityExpressionHandler
        // that will utilize CustomPermissionEvaluator

        val handler =
            DefaultMethodSecurityExpressionHandler()
        // add the PermissionEvaluator
        handler.setPermissionEvaluator(permissionEvaluator)
        return handler
    }
}
