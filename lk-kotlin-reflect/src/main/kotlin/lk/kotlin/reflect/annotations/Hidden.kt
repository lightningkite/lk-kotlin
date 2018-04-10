package lk.kotlin.reflect.annotations

import lk.kotlin.reflect.findType
import java.util.*
import kotlin.reflect.KAnnotatedElement


@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Target(AnnotationTarget.PROPERTY)
annotation class Hidden()

private val kPropertyDoNotModify = WeakHashMap<KAnnotatedElement, Boolean>()
val KAnnotatedElement.hidden: Boolean
    get() = kPropertyDoNotModify.getOrPut(this) {
        annotations.findType<Hidden>() != null
    }