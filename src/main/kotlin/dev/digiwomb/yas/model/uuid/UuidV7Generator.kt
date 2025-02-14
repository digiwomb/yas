package dev.digiwomb.yas.model.uuid

import org.hibernate.annotations.IdGeneratorType

@IdGeneratorType(UuidV7GeneratorType::class)
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class UuidV7Generator
