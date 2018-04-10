package lk.kotlin.reflect.annotations

import lk.kotlin.reflect.classedName
import lk.kotlin.reflect.findType
import lk.kotlin.reflect.nameify
import java.util.*
import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KClass


@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY)
annotation class Description(val text: String)

private val kPropertyDescription = WeakHashMap<KAnnotatedElement, String>()
val KAnnotatedElement.description: String?
    get() = kPropertyDescription.getOrPut(this) {
        annotations.findType<Description>()?.text
    }