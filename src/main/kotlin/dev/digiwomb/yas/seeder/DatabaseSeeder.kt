package dev.digiwomb.yas.seeder

import dev.digiwomb.yas.model.Users
import dev.digiwomb.yas.repository.UsersRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.repository.CrudRepository
import org.springframework.transaction.annotation.Transactional

@Configuration
class DataSeeder(
    private val seedData: List<DataProvider<*>>,
    private val repositories: List<CrudRepository<*, *>>,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @Bean
    @Transactional
    fun seedDatabase(): CommandLineRunner {
        return CommandLineRunner {
            val nonEmptyRepositories = repositories.filter { it.count() > 0 }

            log.info("Start seeding...")

            if (repositories.any { it.count() > 0 }) {
                log.info("Database ist already seeded. No entries will be inserted.")

                val logText: String = ""
                logText.plus("Following model classes are already seeded: ")
                nonEmptyRepositories.forEach { logText.plus("${it.javaClass.simpleName} ") }
                log.debug(logText)
                return@CommandLineRunner
            }

            seedData.forEach { provider ->
                log.info("Seeding data provider ${provider.javaClass.simpleName}")
                when (val data = provider.getData()) {
                    is List<*> -> {
                        if (data.isNotEmpty()) {
                            when (data.first()) {
                                is Users -> (repositories.find { it is UsersRepository } as? UsersRepository)?.saveAll(data as List<Users>)
                            }
                        }
                    }
                }
            }
            log.info("Finished seeding.")
        }
    }
}
