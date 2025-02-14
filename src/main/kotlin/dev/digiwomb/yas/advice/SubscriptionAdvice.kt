package dev.digiwomb.yas.advice

import dev.digiwomb.yas.exception.SubscriptionNotFoundException
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*
import kotlin.jvm.Throws

@Component
@Aspect
class SubscriptionAdvice {

    @Around("execution(* dev.digiwomb.yas.repository.SubscriptionRepository.findById(java.util.UUID))")
    @Throws(
        Throwable::class
    )
    fun checkFindSubscription(proceedingJoinPoint: ProceedingJoinPoint) : Any {
        log.info("[ADVICE] {}", proceedingJoinPoint.signature.name)

        try {
            return proceedingJoinPoint.proceed(
                arrayOf<Any>(
                    UUID.fromString(proceedingJoinPoint.args[0].toString())
                )
            )
        } catch (e: NullPointerException) {
            throw SubscriptionNotFoundException()
        }
    }

    companion object {
        val log = LoggerFactory.getLogger(SubscriptionAdvice::class.java)
    }
}