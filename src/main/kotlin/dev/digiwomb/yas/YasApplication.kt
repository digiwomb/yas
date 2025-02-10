package dev.digiwomb.yas

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class YasApplication

fun main(args: Array<String>) {
	runApplication<YasApplication>(*args)
}
