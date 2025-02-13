package dev.digiwomb.yas.seeder

import liquibase.integration.spring.SpringLiquibase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File
import java.net.URL
import javax.sql.DataSource

@Configuration
class LiquibaseParams() {

    @Bean
    fun liquibase(dataSource: DataSource): SpringLiquibase {
        val params = getConstantsFromPackage("dev.digiwomb.yas.model.mapping")

        return SpringLiquibase().apply {
            this.dataSource = dataSource
            this.changeLog = "classpath:/db/changelog/db.changelog-master.yaml"
            this.setChangeLogParameters(params)
        }
    }

    fun getConstantsFromPackage(packageName: String): Map<String, String> {
        val result = mutableMapOf<String, String>()

        val classLoader = Thread.currentThread().contextClassLoader
        val path = packageName.replace('.', '/')
        val resources = classLoader.getResources(path).toList()

        val classes = resources.flatMap { resource ->
            findClassesInPackage(resource, packageName)
        }

        classes.forEach { className ->
            try {
                val clazz = Class.forName(className) // Load the main class
                extractConstants(clazz, result)

                // Process the Companion Object if it exists
                val companionClazz = clazz.declaredClasses.find { it.simpleName == "Companion" }
                companionClazz?.let { extractConstants(it, result) }

            } catch (e: Exception) {}
        }

        return result
    }

    /**
     * Extracts all `const val` values from a class or companion object.
     * `const val` fields are compiled to `static final` fields in Java.
     */
    private fun extractConstants(clazz: Class<*>, result: MutableMap<String, String>) {
        clazz.declaredFields
            .filter { java.lang.reflect.Modifier.isStatic(it.modifiers) && java.lang.reflect.Modifier.isFinal(it.modifiers) }
            .forEach { field ->
                field.isAccessible = true
                try {
                    val value = field.get(null) as? String // Extract only String constants
                    if (value != null) {
                        result[clazz.simpleName.plus("_").plus(field.name)] = value
                    }
                } catch (e: Exception) {}
            }
    }

    /**
     * Finds all class names inside a package by scanning `.class` files.
     */
    private fun findClassesInPackage(resource: URL, packageName: String): List<String> {
        return try {
            val file = File(resource.toURI())
            if (file.isDirectory) {
                file.walk()
                    .filter { it.isFile && it.extension == "class" }
                    .mapNotNull { getClassNameFromFile(it, packageName) }
                    .filterNot { it.contains("$") } // Ignore inner classes (like Companion)
                    .toList()
            } else emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Converts a `.class` file path into a fully qualified class name.
     */
    private fun getClassNameFromFile(file: File, packageName: String): String? {
        val absolutePath = file.absolutePath.replace(File.separator, ".") // Make Windows-compatible
        val className = absolutePath
            .substringAfterLast("$packageName.")
            .removeSuffix(".class")

        return if (className.contains("$")) null else "$packageName.$className"
    }
}